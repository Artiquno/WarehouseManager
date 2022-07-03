package tk.artiquno.warehouse.management.authentication;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;

    @ElementCollection
    @CollectionTable(name="roles", joinColumns = @JoinColumn(name="user_id"))
    @Column(name = "role")
    private List<String> roles;
}
