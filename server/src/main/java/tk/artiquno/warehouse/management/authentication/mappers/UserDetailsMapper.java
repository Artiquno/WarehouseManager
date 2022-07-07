package tk.artiquno.warehouse.management.authentication.mappers;

import org.mapstruct.Mapper;
import tk.artiquno.warehouse.management.authentication.FullUserDetails;
import tk.artiquno.warehouse.management.authentication.entities.User;
import tk.artiquno.warehouse.management.swagger.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserDetailsMapper {
    User toUser(FullUserDetails userDetails);

    FullUserDetails toUserDetails(User user);

    UserDTO toDto(FullUserDetails userDetails);
}
