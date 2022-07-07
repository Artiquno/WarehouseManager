package tk.artiquno.warehouse.management.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tk.artiquno.warehouse.management.swagger.dto.ItemDTO;

public interface ItemsService {

    /**
     * Create an inventory item
     * @param item Data for the item
     * @return The created item
     */
    ItemDTO createItem(ItemDTO item);

    void deleteItem(Long id);

    Page<ItemDTO> findAllItems(Pageable pagination);

    /**
     * Finds an inventory item
     * @param id The id to search for
     * @return The item
     *
     * @throws javax.persistence.EntityNotFoundException if an item with that id could not be found
     */
    ItemDTO getItemById(Long id);

    /**
     * Updates an item's information
     * @param item The item's updated information
     * @return The updated item
     *
     * @throws javax.persistence.EntityNotFoundException if an item with the given id doesn't exist
     */
    ItemDTO updateItem(ItemDTO item);
}
