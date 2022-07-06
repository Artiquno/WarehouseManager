package tk.artiquno.warehouse.management.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tk.artiquno.warehouse.management.authentication.services.UserService;
import tk.artiquno.warehouse.management.swagger.controllers.UsersApi;
import tk.artiquno.warehouse.management.swagger.dto.UserCredentialsDTO;
import tk.artiquno.warehouse.management.swagger.dto.UserDTO;

import java.util.List;

@RestController
public class UserController implements UsersApi {
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Override
    public ResponseEntity<UserDTO> createUser(UserCredentialsDTO userCredentials) {
        UserDTO createdUser = userService.createUser(userCredentials);
        return ResponseEntity.ok(createdUser);
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(UserDTO userDTO) {
        UserDTO user = userService.updateUser(userDTO);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<Void> removeUser(Long id) {
        userService.removeUser(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<String> createDefaultUser() {
        userService.createDefaultUser();
        return ResponseEntity.ok("Default user created. Try not to delete this one...");
    }
}
