package tk.artiquno.warehouse.management.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity(name = "trucks")
@Data
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String chassis;

    @NotNull
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String licensePlate;
}
