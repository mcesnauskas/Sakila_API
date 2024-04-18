package Sakila_API.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CustomerResponse {
    private int page;
    private int pageSize;
    private int totalPage;
    private List<Customer> listOfCustomers;
}
