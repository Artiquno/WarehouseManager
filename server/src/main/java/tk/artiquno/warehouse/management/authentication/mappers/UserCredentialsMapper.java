package tk.artiquno.warehouse.management.authentication.mappers;

import org.mapstruct.Mapper;
import tk.artiquno.warehouse.management.authentication.entities.User;
import tk.artiquno.warehouse.management.swagger.dto.UserCredentialsDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserCredentialsMapper {
    User toUser(UserCredentialsDTO dto);
    List<User> toUser(List<UserCredentialsDTO> dtos);

    UserCredentialsDTO toUserCredentials(User user);
    List<UserCredentialsDTO> toUserCredentials(List<User> users);
}
