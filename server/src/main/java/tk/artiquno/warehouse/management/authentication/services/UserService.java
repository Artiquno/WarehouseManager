package tk.artiquno.warehouse.management.authentication.services;

import tk.artiquno.warehouse.management.authentication.User;
import tk.artiquno.warehouse.management.authentication.dto.CreateUserDTO;

public interface UserService {
    User getUserByUsername(String username);

    void createUser(CreateUserDTO userInfo);
}
