package tk.artiquno.warehouse.management.mappers;

import org.mapstruct.Mapper;
import tk.artiquno.warehouse.management.entities.Order;
import tk.artiquno.warehouse.management.swagger.dto.BasicOrderDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BasicOrdersMapper {
    Order toOrder(BasicOrderDTO dto);
    List<Order> toOrder(List<BasicOrderDTO> dtos);

    BasicOrderDTO toDto(Order order);
    List<BasicOrderDTO> toDto(List<Order> orders);
}
