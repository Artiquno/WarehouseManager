package tk.artiquno.warehouse.management.mappers;

import org.mapstruct.Mapper;
import tk.artiquno.warehouse.management.entities.Item;
import tk.artiquno.warehouse.management.swagger.dto.ItemDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemsMapper {
    Item toItem(ItemDTO dto);
    List<Item> toItem(List<ItemDTO> dtos);

    ItemDTO toDto(Item item);
    List<ItemDTO> toDto(List<Item> items);
}
