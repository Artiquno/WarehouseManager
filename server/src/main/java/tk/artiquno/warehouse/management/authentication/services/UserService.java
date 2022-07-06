package tk.artiquno.warehouse.management.authentication.services;

import org.springframework.data.domain.Pageable;
import tk.artiquno.warehouse.management.authentication.User;
import tk.artiquno.warehouse.management.swagger.dto.UserCredentialsDTO;
import tk.artiquno.warehouse.management.swagger.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers(Pageable pageable);

    /**
     * Finds the user with the given id. If we can't find it,
     * it throws an exception
     * @param id The id to search for
     * @return The user
     *
     * @throws javax.persistence.EntityNotFoundException if a user with the
     * given id can not be found
     */
    UserDTO getUserById(long id);

    /**
     * Finds the user with the given username. If the user is not active
     * it is considered not found
     * @param username The username to look for
     * @return The user
     *
     * @throws javax.persistence.EntityNotFoundException if a user with the
     * given username can not be found
     */
    User getActiveUserByUsername(String username);

    /**
     * Creates a user with the given credentials
     *
     * @param userCredentials The credentials to use for the new user
     * @return The created user
     * @throws tk.artiquno.warehouse.management.authentication.UsernameExistsException if another user with the given username already exists
     */
    UserDTO createUser(UserCredentialsDTO userCredentials);

    List<String> getRolesByUsername(String username);

    /**
     * Soft-deletes a user
     * @param id The id of the user to remove
     */
    void removeUser(long id);

    /**
     * Creates a default admin user *if* there are no other users in the db
     *
     * @throws tk.artiquno.warehouse.management.exceptions.ForbiddenException if
     * a user already exists in the database
     */
    void createDefaultUser();

    /**
     * Update a user's information
     * @param user The user's updated information
     * @return The updated user
     *
     * @throws javax.persistence.EntityNotFoundException if a user
     * with the given id does not exist
     */
    UserDTO updateUser(UserDTO user);
}
