package Sakila_API.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", columnDefinition = "SMALLINT UNSIGNED")
    private Short customerId;
    @Column(name = "store_id",  columnDefinition = "SMALLINT UNSIGNED")
    private Byte storeId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "address_id", columnDefinition = "SMALLINT UNSIGNED")
    private Short addressId;
    @Column(name = "active")
    private Integer active;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonManagedReference
    @ToString.Exclude
    private List<Rental> rentals = new ArrayList<>();
}
