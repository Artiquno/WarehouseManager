package tk.artiquno.warehouse.management.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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

        Order createdOrder = ordersRepo.save(order);
        return ordersMapper.toDto(createdOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        ordersRepo.deleteById(id);
    }

    @Override
    public Page<BasicOrderDTO> findAllOrdersWithStatus(Pageable pagination, OrderStatus status) {
        return ordersRepo.findAllByStatus(pagination, status)
                .map(basicOrdersMapper::toDto);
    }

    @Override
    public OrderDTO findOrderById(Long id) {
        return ordersRepo.findById(id)
                .map(ordersMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        Order existingOrder = ordersRepo.findById(orderDTO.getId())
                .orElseThrow(EntityNotFoundException::new);

        Order newOrder = ordersMapper.toOrder(orderDTO);
        BeanUtils.copyProperties(newOrder, existingOrder, "submittedDate", "status", "owner");

        Order updatedOrder = ordersRepo.save(existingOrder);
        return ordersMapper.toDto(updatedOrder);
    }

    @Override
    public OrderDTO updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order existingOrder = ordersRepo.findById(orderId)
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
            StringBuilder message = new StringBuilder(String.format("Can't move order to %s from %s. Order" +
                    "needs to be in one of these states to be set as %s: ", newState, oldState, newState));
            for (OrderStatus status : availableTransitions) {
                message.append(status.getValue());
            }
            throw new ConflictException(message.toString());
        }

        return true;
    }
}
