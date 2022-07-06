package tk.artiquno.warehouse.management.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.artiquno.warehouse.management.authentication.mappers.UserCredentialsMapper;
import tk.artiquno.warehouse.management.authentication.mappers.UserMapper;
import tk.artiquno.warehouse.management.authentication.services.UserServiceImpl;
import tk.artiquno.warehouse.management.swagger.dto.UserCredentialsDTO;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final String NEW_USERNAME = "NewUsername";
    private static final String EXISTING_USERNAME = "ExistingUsername";
    private static final String PLAIN_PASSWORD = "PlainPassword";

    @Mock
    private UserRepo userRepo;

    @Spy
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private String encryptedPassword;

    @Spy
    private UserCredentialsMapper userCredentialsMapper = Mappers.getMapper(UserCredentialsMapper.class);

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        encryptedPassword = encoder.encode(PLAIN_PASSWORD);
    }

    @Test
    void createUser() {
        Mockito.when(userRepo.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.empty());

        final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        UserCredentialsDTO userInfo = new UserCredentialsDTO();
        userInfo.setUsername(NEW_USERNAME);
        userInfo.setPassword(PLAIN_PASSWORD);

        userService.createUser(userInfo);
        verify(userRepo).save(captor.capture());

        final User savedUser = captor.getValue();
        assertEquals(NEW_USERNAME, savedUser.getUsername());
        // encoder.encode will be called also in setup and apparently Mockito keeps track of that
        verify(encoder, atLeast(2)).encode(PLAIN_PASSWORD);
    }

    @Test
    public void createUser_ExistingUsername() {
        User badUser = new User();
        badUser.setUsername(EXISTING_USERNAME);
        badUser.setPassword(encryptedPassword);

        Mockito.when(userRepo.findByUsername(EXISTING_USERNAME))
                .thenReturn(Optional.of(badUser));

        assertThrows(UsernameExistsException.class, () -> {
            UserCredentialsDTO badInfo = new UserCredentialsDTO();
            badInfo.setUsername(EXISTING_USERNAME);
            userService.createUser(badInfo);
        });
    }
}