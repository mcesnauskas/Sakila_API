package Sakila_API.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "rental")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Integer rentalId;
    @Column(name = "rental_date")
    private LocalDateTime rentalDate;
    @Column(name = "inventory_id", columnDefinition = "MEDIUMINT UNSIGNED")
    private Integer inventoryId;
    @Column(name = "return_date")
    private LocalDateTime returnDate;
    @Column(name = "staff_id", columnDefinition = "TINYINT UNSIGNED")
    private Integer staffId;
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
    @Column(name = "customer_id", columnDefinition = "SMALLINT UNSIGNED")
    private Short customerId;

    @ManyToOne
    @JoinColumn(name = "customer_id",
            insertable = false,
            updatable = false
    )
    @PrimaryKeyJoinColumn()
    @JsonBackReference
    private Customer customer;
}
