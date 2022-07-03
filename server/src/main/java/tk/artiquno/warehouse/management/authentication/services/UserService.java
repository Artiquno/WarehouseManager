package tk.artiquno.warehouse.management.authentication.services;

import tk.artiquno.warehouse.management.authentication.User;
import tk.artiquno.warehouse.management.authentication.dto.CreateUserDTO;

import java.util.List;

public interface UserService {
    User getUserByUsername(String username);

    void createUser(CreateUserDTO userInfo);

    List<String> getRolesByUsername(String username);
}
