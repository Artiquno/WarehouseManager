package tk.artiquno.warehouse.management.authentication;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;

    private String email;

    @ElementCollection
    @CollectionTable(name="roles", joinColumns = @JoinColumn(name="user_id"))
    @Column(name = "role")
    private List<String> roles;

    @ColumnDefault("true")
    private boolean isActive = true;
}
