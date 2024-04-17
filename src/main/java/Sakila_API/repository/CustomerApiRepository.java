package Sakila_API.repository;

import Sakila_API.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerApiRepository extends CrudRepository<Customer, Integer> {

    @Query(
            value = "SELECT * FROM customer",
            countQuery = "SELECT count(*) FROM customer",
            nativeQuery = true)
    Page<Customer> fetchAllCustomers(Pageable pageable);

    @Query(
            value = "SELECT * FROM customer WHERE customer_id = :id",
            nativeQuery = true
    )
    Optional<Customer> fetchCustomerById(Integer id);
}
