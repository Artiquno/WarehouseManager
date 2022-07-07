package tk.artiquno.warehouse.management.mappers;

import org.mapstruct.Mapper;
import tk.artiquno.warehouse.management.entities.Order;
import tk.artiquno.warehouse.management.swagger.dto.OrderDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdersMapper {
    Order toOrder(OrderDTO dto);
    List<Order> toOrder(List<OrderDTO> dtos);

    OrderDTO toDto(Order order);
    List<OrderDTO> toDto(List<Order> orders);
}
