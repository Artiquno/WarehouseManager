package tk.artiquno.warehouse.management.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tk.artiquno.warehouse.management.authentication.FullUserDetails;
import tk.artiquno.warehouse.management.authentication.entities.Roles;
import tk.artiquno.warehouse.management.authentication.entities.User;
import tk.artiquno.warehouse.management.entities.Order;
import tk.artiquno.warehouse.management.exceptions.ConflictException;
import tk.artiquno.warehouse.management.mappers.BasicOrdersMapper;
import tk.artiquno.warehouse.management.mappers.OrdersMapper;
import tk.artiquno.warehouse.management.repositories.OrdersRepo;
import tk.artiquno.warehouse.management.swagger.dto.BasicOrderDTO;
import tk.artiquno.warehouse.management.swagger.dto.OrderDTO;
import tk.artiquno.warehouse.management.swagger.dto.OrderStatus;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepo ordersRepo;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private BasicOrdersMapper basicOrdersMapper;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = ordersMapper.toOrder(orderDTO);
        order.setStatus(OrderStatus.CREATED);
        order.setSubmittedDate(OffsetDateTime.now());

        FullUserDetails principal = getAuthenticatedUser();
        User owner = new User();
        owner.setId(principal.getId());
        order.setOwner(owner);

        Order createdOrder = ordersRepo.save(order);
        return ordersMapper.toDto(createdOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        ordersRepo.deleteById(id);
    }

    @Override
    public List<Order> findAllOrdersForDelivery() {
        return ordersRepo.findAllByStatus(OrderStatus.APPROVED);
    }

    @Override
    public Page<BasicOrderDTO> findAllOrdersWithStatus(Pageable pagination, OrderStatus status) {
        final FullUserDetails userDetails = getAuthenticatedUser();

        User exampleOwner = new User();
        exampleOwner.setId(userDetails.getId());

        Order exampleOrder = new Order();
        exampleOrder.setOwner(exampleOwner);
        exampleOrder.setStatus(status);

        // A miracle of technology!
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id")
                .withMatcher("status", ExampleMatcher.GenericPropertyMatcher::ignoreCase);

        if(userDetails.getRoles().contains(Roles.SYSTEM_ADMIN.getValue()))
        {
            // Alternatively you could just leave owner as null
            matcher = matcher.withIgnorePaths("owner.id");
        }
        Example<Order> example = Example.of(exampleOrder, matcher);

        return ordersRepo.findAll(example, pagination)
                .map(basicOrdersMapper::toDto);
    }

    @Override
    public OrderDTO findOrderById(Long id) {
        final FullUserDetails userDetails = getAuthenticatedUser();

        Optional<Order> order;
        if(userDetails.getRoles().contains(Roles.WAREHOUSE_MANAGER.getValue()))
        {
            order = ordersRepo.findById(id);
        }
        else
        {
            order = ordersRepo.findByIdAndOwnerId(id, userDetails.getId());
        }
        return order.map(ordersMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        final FullUserDetails userDetails = getAuthenticatedUser();

        Order existingOrder = ordersRepo.findByIdAndOwnerId(orderDTO.getId(), userDetails.getId())
                .orElseThrow(EntityNotFoundException::new);

        // Only allow orders with these statuses to be updated
        final List<OrderStatus> possibleStatuses = Arrays.asList(OrderStatus.CREATED, OrderStatus.DECLINED);
        if(!possibleStatuses.contains(existingOrder.getStatus()))
        {
            throw new ConflictException("You can only update orders with CREATED or DECLINED status");
        }

        Order newOrder = ordersMapper.toOrder(orderDTO);
        BeanUtils.copyProperties(newOrder, existingOrder, "submittedDate", "status", "owner");

        Order updatedOrder = ordersRepo.save(existingOrder);
        return ordersMapper.toDto(updatedOrder);
    }

    @Override
    public OrderDTO updateOrderStatus(Long orderId, OrderStatus newStatus) {
        FullUserDetails userDetails = getAuthenticatedUser();

        Optional<Order> existingOrderOptional;
        if(userDetails.getRoles().contains(Roles.WAREHOUSE_MANAGER.getValue()))
        {
            existingOrderOptional = ordersRepo.findById(orderId);
        }
        else
        {
            existingOrderOptional = ordersRepo.findByIdAndOwnerId(orderId, userDetails.getId());
        }
        Order existingOrder = existingOrderOptional
                .orElseThrow(EntityNotFoundException::new);

        // TODO: Replace with a proper state machine
        orderStatusStateMachineEmulatorKindOf(existingOrder.getStatus(), newStatus);

        existingOrder.setStatus(newStatus);
        return ordersMapper.toDto(ordersRepo.save(existingOrder));
    }

    /**
     * A sort-of-but-not-really state machine-y logic for orders.
     * This should be replaced with a proper state machine, but
     * i'll just hardcode it for now.
     * If the new status can't be reached, it will throw
     * @param oldState The old state to move from
     * @param newState The new staet to move to
     *
     * @deprecated Use a proper state machine you lazy bloke!
     *
     * @throws tk.artiquno.warehouse.management.exceptions.ConflictException
     * if the order can't be placed in the new state
     */
    @Deprecated
    private boolean orderStatusStateMachineEmulatorKindOf(OrderStatus oldState, OrderStatus newState) {
        final Map<OrderStatus, List<OrderStatus>> transitions = new HashMap<>();
        transitions.put(OrderStatus.CREATED,
                Collections.emptyList());
        transitions.put(OrderStatus.AWAITING_APPROVAL,
                Arrays.asList(OrderStatus.CREATED,
                              OrderStatus.DECLINED));

        transitions.put(OrderStatus.APPROVED,
                List.of(OrderStatus.AWAITING_APPROVAL));

        transitions.put(OrderStatus.DECLINED,
                List.of(OrderStatus.AWAITING_APPROVAL));

        transitions.put(OrderStatus.UNDER_DELIVERY,
                List.of(OrderStatus.APPROVED));

        transitions.put(OrderStatus.FULFILLED,
                List.of(OrderStatus.UNDER_DELIVERY));

        transitions.put(OrderStatus.CANCELLED,
                Arrays.asList(OrderStatus.CREATED,
                              OrderStatus.AWAITING_APPROVAL,
                              OrderStatus.APPROVED,
                              OrderStatus.DECLINED));
        
        List<OrderStatus> availableTransitions = transitions.getOrDefault(newState, null);
        if(availableTransitions == null)
        {
            throw new ConflictException("New state doesn't exist???");
        }

        if(!availableTransitions.contains(oldState))
        {
            StringBuilder message = new StringBuilder(String.format("Can't move order to %s from %s. Order " +
                    "needs to be in one of these states to be set as %s: ", newState, oldState, newState));
            for (OrderStatus status : availableTransitions) {
                message.append(status.getValue());
                message.append(", ");
            }
            throw new ConflictException(message.toString());
        }

        return true;
    }

    private FullUserDetails getAuthenticatedUser() {
        return (FullUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
