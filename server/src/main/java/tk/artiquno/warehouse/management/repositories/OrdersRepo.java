package tk.artiquno.warehouse.management.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tk.artiquno.warehouse.management.entities.Order;
import tk.artiquno.warehouse.management.swagger.dto.BasicOrderDTO;
import tk.artiquno.warehouse.management.swagger.dto.OrderStatus;

@Repository
public interface OrdersRepo extends PagingAndSortingRepository<Order, Long> {
    Page<Order> findAllByStatus(Pageable pagination, OrderStatus status);
}
