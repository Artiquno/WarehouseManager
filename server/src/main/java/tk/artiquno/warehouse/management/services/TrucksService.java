package tk.artiquno.warehouse.management.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tk.artiquno.warehouse.management.swagger.dto.TruckDTO;

public interface TrucksService {
    TruckDTO createTruck(TruckDTO truckDTO);

    void deleteTruck(Long id);

    /**
     * Finds a truck with the given id and returns it
     * @param id The id to look for
     * @return The truck with the id that we were looking for
     *
     * @throws javax.persistence.EntityNotFoundException
     * if we couldn't find the truck with the id that we were looking for
     */
    TruckDTO getTruckById(Long id);

    /**
     * Update's a truck with the given information
     * @param truckDTO The new information. The id is used to identify an existing truck
     * @return The updated truck
     *
     * @throws javax.persistence.EntityNotFoundException if we couldn't find a truck with that id
     */
    TruckDTO updateTruck(TruckDTO truckDTO);

    Page<TruckDTO> findAllTrucks(Pageable pageable);
}
