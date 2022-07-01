package tk.artiquno.warehouse.management.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@lombok.Data
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int chassis;
    String licensePlate;
}
