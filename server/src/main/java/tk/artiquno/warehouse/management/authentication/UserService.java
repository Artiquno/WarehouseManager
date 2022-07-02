package tk.artiquno.warehouse.management.authentication;

import tk.artiquno.warehouse.management.authentication.dto.CreateUserDTO;

public interface UserService {
    User getUser(String username);

    void createUser(CreateUserDTO userInfo);
}
