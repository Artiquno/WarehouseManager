package tk.artiquno.warehouse.management.authentication.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tk.artiquno.warehouse.management.authentication.entities.User;

import java.util.Optional;

@Repository
public interface UserRepo extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndIsActive(String username, boolean isActive);
}
