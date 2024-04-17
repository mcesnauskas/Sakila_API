//package Sakila_API.model;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "rental")
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//public class Rental {
//    @Id
//    @Column(name = "rental_id")
//    private Integer rentalId;
//    @Column(name = "rental_date")
//    private LocalDateTime rentalDate;
//    @Column(name = "inventory_id")
//    private Integer inventoryId;
//    @Column(name = "customer_id")
//    private Integer customerId;
//    @Column(name = "return_date")
//    private LocalDateTime returnDate;
//    @Column(name = "staff_id")
//    private Integer staffId;
//    @Column(name = "last_update")
//    private LocalDateTime lastUpdate;
//
//    @ManyToOne
//    @JoinColumn(name = "customer_id")
//    @PrimaryKeyJoinColumn
//    @JsonBackReference
//    private Customer customer;
//}
