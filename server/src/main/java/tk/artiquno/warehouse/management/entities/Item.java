package tk.artiquno.warehouse.management.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String name;
    private int quantityInStock;
    private float unitPrice;

    @OneToMany(mappedBy = "item")
    List<OrderedItem> orderedItems;
}
