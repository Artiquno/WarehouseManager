package tk.artiquno.warehouse.management.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tk.artiquno.warehouse.management.entities.Order;
import tk.artiquno.warehouse.management.swagger.dto.BasicOrderDTO;
import tk.artiquno.warehouse.management.swagger.dto.OrderDTO;
import tk.artiquno.warehouse.management.swagger.dto.OrderStatus;

import java.util.List;

public interface OrdersService {

    OrderDTO createOrder(OrderDTO orderDTO);

    void deleteOrder(Long id);

    List<Order> findAllOrdersForDelivery();
    Page<BasicOrderDTO> findAllOrdersWithStatus(Pageable pagination, OrderStatus status);

    OrderDTO findOrderById(Long id);

    OrderDTO updateOrder(OrderDTO orderDTO);

    OrderDTO updateOrderStatus(Long orderId, OrderStatus newStatus);
}
