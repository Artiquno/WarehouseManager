package tk.artiquno.warehouse.management.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.artiquno.warehouse.management.authentication.dto.CreateUserDTO;
import tk.artiquno.warehouse.management.authentication.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody CreateUserDTO userInfo) {
        userService.createUser(userInfo);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create-default")
    public ResponseEntity<String> createDefaultUser() {
        userService.createDefaultUser();
        return ResponseEntity.ok("Default user created. Try not to delete this one...");
    }
}
