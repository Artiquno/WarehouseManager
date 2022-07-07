package tk.artiquno.warehouse.management.authentication;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import tk.artiquno.warehouse.management.entities.Order;

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

    // This should be available only for clients, but eh
    @OneToMany
    private List<Order> orders;

    @ElementCollection
    @CollectionTable(name="roles", joinColumns = @JoinColumn(name="user_id"))
    @Column(name = "role")
    private List<String> roles;

    @ColumnDefault("true")
    private boolean isActive = true;
}
