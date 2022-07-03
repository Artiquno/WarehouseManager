package tk.artiquno.warehouse.management.authentication.services;

import tk.artiquno.warehouse.management.authentication.User;
import tk.artiquno.warehouse.management.authentication.dto.CreateUserDTO;

import java.util.List;

public interface UserService {
    User getUserByUsername(String username);

    void createUser(CreateUserDTO userInfo);

    List<String> getRolesByUsername(String username);

    /**
     * Creates a default admin user *if* there are no other users in the db
     *
     * @throws tk.artiquno.warehouse.management.exceptions.ForbiddenException if
     * a user already exists in the database
     */
    void createDefaultUser();
}
