package tk.artiquno.warehouse.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tk.artiquno.warehouse.management.services.TrucksService;
import tk.artiquno.warehouse.management.swagger.controllers.TrucksApi;
import tk.artiquno.warehouse.management.swagger.dto.TruckDTO;

import java.util.List;

@RestController
public class TrucksController implements TrucksApi {
    @Autowired
    private TrucksService trucksService;

    @Override
    public ResponseEntity<TruckDTO> createTruck(TruckDTO truckDTO) {
        return ResponseEntity.ok(trucksService.createTruck(truckDTO));
    }

    @Override
    public ResponseEntity<Void> deleteTruck(Long id) {
        trucksService.deleteTruck(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TruckDTO> getTruck(Long id) {
        return ResponseEntity.ok(trucksService.getTruckById(id));
    }

    @Override
    public ResponseEntity<TruckDTO> updateTruck(TruckDTO truckDTO) {
        return ResponseEntity.ok(trucksService.updateTruck(truckDTO));
    }

    @Override
    public ResponseEntity<Page> getAllTrucks(Pageable pageable) {
        return ResponseEntity.ok(trucksService.findAllTrucks(pageable));
    }
}
