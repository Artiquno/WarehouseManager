package tk.artiquno.warehouse.management.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tk.artiquno.warehouse.management.entities.Item;

@Repository
public interface ItemsRepo extends PagingAndSortingRepository<Item, Long> {

}
