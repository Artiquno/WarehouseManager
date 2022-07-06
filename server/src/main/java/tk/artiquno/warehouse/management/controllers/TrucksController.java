package tk.artiquno.warehouse.management.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import tk.artiquno.warehouse.management.swagger.controllers.TrucksApi;
import tk.artiquno.warehouse.management.swagger.dto.TruckDTO;

import java.util.Collections;
import java.util.List;

public class TrucksController implements TrucksApi {
    @Override
    public ResponseEntity<List<TruckDTO>> getAllTrucks(Pageable pageable) {
        TruckDTO truck = new TruckDTO()
                .chassis("5GZCZ43D13S812715")
                .licensePlate("6NRLKN8");

        return ResponseEntity.ok(Collections.singletonList(truck));
    }
}
