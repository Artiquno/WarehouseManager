package tk.artiquno.warehouse.management.entities;

import lombok.Data;
import tk.artiquno.warehouse.management.authentication.User;
import tk.artiquno.warehouse.management.swagger.dto.OrderStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(nullable = false)
    private OffsetDateTime submittedDate;
    @NotNull
    @Column(nullable = false)
    private OffsetDateTime deadlineDate;
    @NotNull
    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany(cascade = { CascadeType.ALL })
    private List<OrderedItem> items;

    @NotNull
    @ManyToOne(optional = false)
    private User owner;
}
