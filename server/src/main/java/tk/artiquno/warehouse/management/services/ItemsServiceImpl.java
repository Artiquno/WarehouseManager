package tk.artiquno.warehouse.management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.artiquno.warehouse.management.entities.Item;
import tk.artiquno.warehouse.management.mappers.ItemsMapper;
import tk.artiquno.warehouse.management.repositories.ItemsRepo;
import tk.artiquno.warehouse.management.swagger.dto.ItemDTO;

import javax.persistence.EntityNotFoundException;

@Service
public class ItemsServiceImpl implements ItemsService {
    @Autowired
    private ItemsRepo itemsRepo;

    @Autowired
    private ItemsMapper itemsMapper;

    @Override
    public ItemDTO createItem(ItemDTO item) {
        Item updatedItem = itemsRepo.save(itemsMapper.toItem(item));
        return itemsMapper.toDto(updatedItem);
    }

    @Override
    public void deleteItem(Long id) {
        itemsRepo.deleteById(id);
    }

    @Override
    public Page<ItemDTO> findAllItems(Pageable pagination) {
        return itemsRepo.findAll(pagination)
                .map(itemsMapper::toDto);
    }

    @Override
    public ItemDTO getItemById(Long id) {
        return itemsRepo.findById(id)
                .map(itemsMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public ItemDTO updateItem(ItemDTO item) {
        Item updated = updateItem(itemsMapper.toItem(item));
        return itemsMapper.toDto(updated);
    }

    @Override
    public Item updateItem(Item item) {
        if(!itemsRepo.existsById(item.getId())) {
            throw new EntityNotFoundException();
        }

        return itemsRepo.save(item);
    }
}
