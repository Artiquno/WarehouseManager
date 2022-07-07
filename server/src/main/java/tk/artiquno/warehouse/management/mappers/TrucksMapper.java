package tk.artiquno.warehouse.management.mappers;

import org.mapstruct.Mapper;
import tk.artiquno.warehouse.management.entities.Truck;
import tk.artiquno.warehouse.management.swagger.dto.TruckDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrucksMapper {
    Truck toTruck(TruckDTO dto);
    List<Truck> toTruck(List<TruckDTO> dtos);

    TruckDTO toDto(Truck truck);
    List<TruckDTO> toDto(List<Truck> trucks);
}
