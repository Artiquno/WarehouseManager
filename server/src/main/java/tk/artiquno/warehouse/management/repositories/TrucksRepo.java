package tk.artiquno.warehouse.management.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tk.artiquno.warehouse.management.entities.Truck;

@Repository
public interface TrucksRepo extends PagingAndSortingRepository<Truck, Long> {
}
