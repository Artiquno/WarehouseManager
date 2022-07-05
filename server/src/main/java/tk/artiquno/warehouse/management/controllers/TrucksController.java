package tk.artiquno.warehouse.management.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tk.artiquno.warehouse.management.swagger.controllers.TrucksApi;
import tk.artiquno.warehouse.management.swagger.dto.TruckDTO;

import java.util.Collections;
import java.util.List;

@RestController
public class TrucksController implements TrucksApi {
    @Override
    public ResponseEntity<List<TruckDTO>> getAllTrucks() {
        TruckDTO truck = new TruckDTO()
                .chassis(42)
                .licensePlate("6NRLKN8");

        return ResponseEntity.ok(Collections.singletonList(truck));
    }
}
