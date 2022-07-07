package tk.artiquno.warehouse.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tk.artiquno.warehouse.management.services.ItemsService;
import tk.artiquno.warehouse.management.swagger.controllers.ItemsApi;
import tk.artiquno.warehouse.management.swagger.dto.ItemDTO;

import java.util.List;

@RestController
public class ItemsController implements ItemsApi {
    @Autowired
    private ItemsService itemsService;
    @Override
    public ResponseEntity<ItemDTO> createItem(ItemDTO itemDTO) {
        return ResponseEntity.ok(itemsService.createItem(itemDTO));
    }

    @Override
    public ResponseEntity<Void> deleteItem(Long id) {
        itemsService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page> getAllItems(Pageable pagination) {
        return ResponseEntity.ok(itemsService.findAllItems(pagination));
    }

    @Override
    public ResponseEntity<ItemDTO> getItem(Long id) {
        return ResponseEntity.ok(itemsService.getItemById(id));
    }

    @Override
    public ResponseEntity<ItemDTO> updateItem(ItemDTO itemDTO) {
        return ResponseEntity.ok(itemsService.updateItem(itemDTO));
    }
}
