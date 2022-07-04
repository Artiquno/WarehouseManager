package tk.artiquno.warehouse.management.authentication.dto;

import lombok.Data;

import java.util.List;

@Data
public class SuccessfulAuthenticationDTO {
    private String username;
    private List<String> roles;
}
