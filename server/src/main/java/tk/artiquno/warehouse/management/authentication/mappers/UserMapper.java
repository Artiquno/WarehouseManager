package tk.artiquno.warehouse.management.authentication.mappers;

import org.mapstruct.Mapper;
import tk.artiquno.warehouse.management.authentication.User;
import tk.artiquno.warehouse.management.swagger.dto.UserDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDTO dto);
    List<User> toUser(List<UserDTO> dtos);

    UserDTO toUserDTO(User user);
    List<UserDTO> toUserDTO(List<User> users);
}
