package tk.artiquno.warehouse.management.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tk.artiquno.warehouse.management.entities.Order;
import tk.artiquno.warehouse.management.swagger.dto.OrderStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepo extends PagingAndSortingRepository<Order, Long> {
    Page<Order> findAll(Example<Order> example, Pageable pageable);

    Optional<Order> findByIdAndOwnerId(Long id, Long ownerId);

    List<Order> findAllByStatus(OrderStatus status);
}
