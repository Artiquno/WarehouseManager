package tk.artiquno.warehouse.management.authentication.dto;

import lombok.Data;

@Data
public class CreateUserDTO {
    private String username;
    private String password;
}
