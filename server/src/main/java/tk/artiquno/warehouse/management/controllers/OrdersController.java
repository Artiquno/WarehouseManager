package tk.artiquno.warehouse.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tk.artiquno.warehouse.management.services.OrdersService;
import tk.artiquno.warehouse.management.swagger.controllers.OrdersApi;
import tk.artiquno.warehouse.management.swagger.dto.DeliveryDTO;
import tk.artiquno.warehouse.management.swagger.dto.OrderDTO;
import tk.artiquno.warehouse.management.swagger.dto.OrderStatus;

@RestController
public class OrdersController implements OrdersApi {
    @Autowired
    private OrdersService ordersService;

    @Override
    public ResponseEntity<OrderDTO> createOrder(OrderDTO orderDTO) {
        return ResponseEntity.ok(ordersService.createOrder(orderDTO));
    }

    @Override
    public ResponseEntity<Void> deleteOrder(Long id) {
        ordersService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page> getAllOrders(OrderStatus status, Pageable pagination) {
        return ResponseEntity.ok(ordersService.findAllOrdersWithStatus(pagination, status));
    }

    @Override
    public ResponseEntity<OrderDTO> getOrder(Long id) {
        return ResponseEntity.ok(ordersService.findOrderById(id));
    }

    @Override
    public ResponseEntity<OrderDTO> updateOrder(OrderDTO orderDTO) {
        return ResponseEntity.ok(ordersService.updateOrder(orderDTO));
    }

    @Override
    public ResponseEntity<Void> scheduleDelivery(DeliveryDTO deliveryDTO) {
        return OrdersApi.super.scheduleDelivery(deliveryDTO);
    }

    @Override
    public ResponseEntity<OrderDTO> approveOrder(Long id) {
        return ResponseEntity.ok(ordersService.updateOrderStatus(id, OrderStatus.APPROVED));
    }

    @Override
    public ResponseEntity<OrderDTO> cancelOrder(Long id) {
        return ResponseEntity.ok(ordersService.updateOrderStatus(id, OrderStatus.CANCELLED));
    }

    @Override
    public ResponseEntity<OrderDTO> declineOrder(Long id) {
        return ResponseEntity.ok(ordersService.updateOrderStatus(id, OrderStatus.DECLINED));
    }

    @Override
    public ResponseEntity<OrderDTO> submitOrder(Long id) {
        return ResponseEntity.ok(ordersService.updateOrderStatus(id, OrderStatus.AWAITING_APPROVAL));
    }

    @Override
    public ResponseEntity<OrderDTO> fulfillOrder(Long id) {
        return ResponseEntity.ok(ordersService.updateOrderStatus(id, OrderStatus.FULFILLED));
    }
}
