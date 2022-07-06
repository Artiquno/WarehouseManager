package tk.artiquno.warehouse.management.authentication;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
