package tk.artiquno.warehouse.management.authentication.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateUserDTO {
    private String username;
    private String password;
    private List<String> roles;
}
