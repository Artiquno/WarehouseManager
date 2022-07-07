package tk.artiquno.warehouse.management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.artiquno.warehouse.management.entities.Truck;
import tk.artiquno.warehouse.management.mappers.TrucksMapper;
import tk.artiquno.warehouse.management.repositories.TrucksRepo;
import tk.artiquno.warehouse.management.swagger.dto.TruckDTO;

import javax.persistence.EntityNotFoundException;

@Service
public class TrucksServiceImpl implements TrucksService {
    @Autowired
    private TrucksRepo trucksRepo;

    @Autowired
    private TrucksMapper trucksMapper;

    @Override
    public TruckDTO createTruck(TruckDTO truckDTO) {
        Truck truck = trucksMapper.toTruck(truckDTO);
        return trucksMapper.toDto(trucksRepo.save(truck));
    }

    @Override
    public void deleteTruck(Long id) {
        trucksRepo.deleteById(id);
    }

    @Override
    public TruckDTO getTruckById(Long id) {
        return trucksRepo.findById(id)
                .map(trucksMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public TruckDTO updateTruck(TruckDTO truckDTO) {
        if(!trucksRepo.existsById(truckDTO.getId()))
        {
            throw new EntityNotFoundException();
        }

        Truck updatedTruck = trucksRepo.save(trucksMapper.toTruck(truckDTO));
        return trucksMapper.toDto(updatedTruck);
    }

    @Override
    public Page<TruckDTO> findAllTrucks(Pageable pageable) {
        return trucksRepo.findAll(pageable)
                .map(trucksMapper::toDto);
    }
}
