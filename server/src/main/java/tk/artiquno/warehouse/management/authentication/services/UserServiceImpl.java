package tk.artiquno.warehouse.management.authentication.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.artiquno.warehouse.management.authentication.User;
import tk.artiquno.warehouse.management.authentication.UserRepo;
import tk.artiquno.warehouse.management.authentication.UsernameExistsException;
import tk.artiquno.warehouse.management.authentication.configurations.SecurityProperties;
import tk.artiquno.warehouse.management.authentication.mappers.UserCredentialsMapper;
import tk.artiquno.warehouse.management.authentication.mappers.UserMapper;
import tk.artiquno.warehouse.management.exceptions.ForbiddenException;
import tk.artiquno.warehouse.management.swagger.dto.UserCredentialsDTO;
import tk.artiquno.warehouse.management.swagger.dto.UserDTO;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserCredentialsMapper userCredentialsMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public List<UserDTO> getAllUsers() {
        return StreamSupport.stream(userRepo.findAll().spliterator(), false)
                .map(userMapper::toUserDTO)
                .toList();
    }

    @Override
    public UserDTO getUserById(long id) {
        User user = userRepo.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return userMapper.toUserDTO(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public UserDTO createUser(UserCredentialsDTO userCredentials) {
        Optional<User> existingUser = userRepo.findByUsername(userCredentials.getUsername());
        if(existingUser.isPresent()) {
            throw new UsernameExistsException("A user with this username already exists");
        }

        User user = userCredentialsMapper.toUser(userCredentials);
        user.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
        user = userRepo.save(user);

        return userMapper.toUserDTO(user);
    }

    @Transactional
    @Override
    public List<String> getRolesByUsername(String username) {
        User user = getUserByUsername(username);
        // The bad .stream().toList() is needed since we have lazy loading
        // and we *have* to load the roles before we exit the method otherwise
        // we will get LazyInitializationException (because then the
        // transaction/session is closed?)
        return user.getRoles().stream()
                .toList();
    }

    @Override
    public UserDTO updateUser(UserDTO user) {
        User existingUser = userRepo.findById(user.getId())
                .orElseThrow(EntityNotFoundException::new);

        BeanUtils.copyProperties(user, existingUser);

        existingUser = userRepo.save(existingUser);
        return userMapper.toUserDTO(existingUser);
    }

    @Override
    public void removeUser(long id) {
        User user = userRepo.findById(id)
                .orElse(null);
        if(user == null)
        {
            return;
        }

        user.setActive(false);
        userRepo.save(user);
    }

    @Override
    public void createDefaultUser() {
        long userCount = userRepo.count();
        if(userCount > 0)
        {
            throw new ForbiddenException("A user already exists");
        }

        UserCredentialsDTO user = userCredentialsMapper.toUserCredentials(securityProperties.getDefaultUser());
        createUser(user);
    }
}
