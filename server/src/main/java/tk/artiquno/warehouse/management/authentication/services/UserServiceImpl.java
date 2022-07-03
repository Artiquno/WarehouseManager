package tk.artiquno.warehouse.management.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.artiquno.warehouse.management.authentication.User;
import tk.artiquno.warehouse.management.authentication.UserRepo;
import tk.artiquno.warehouse.management.authentication.UsernameExistsException;
import tk.artiquno.warehouse.management.authentication.dto.CreateUserDTO;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void createUser(CreateUserDTO userInfo) {
        User user = new User();
        Optional<User> existingUser = userRepo.findByUsername(userInfo.getUsername());
        if(existingUser.isPresent()) {
            throw new UsernameExistsException("A user with this username already exists");
        }
        user.setUsername(userInfo.getUsername());
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepo.save(user);
    }
}
