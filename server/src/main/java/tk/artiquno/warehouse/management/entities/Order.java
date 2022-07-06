package tk.artiquno.warehouse.management.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(nullable = false)
    private Date submittedDate;
    @NotNull
    @Column(nullable = false)
    private Date deadlineDate;
    @NotNull
    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany
    private List<OrderedItem> items;
}
