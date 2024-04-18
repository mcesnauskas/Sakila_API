package Sakila_API.service;

import Sakila_API.model.Customer;
import Sakila_API.model.CustomerResponse;
import Sakila_API.repository.CustomerApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomerApiService {
    private final int defaultPage = 0;
    private final int defaultPageSize = 4;
    private String apiKey = "abc123";
    private String message = "Bad request. Check with Your API administrator";
    public static final String HEADERS_X_API_KEY = "X-Api-Key";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_PAGE_SIZE = "pageSize";

    @Autowired
    private CustomerApiRepository customerRepository;

    public ResponseEntity<?> fetchAllCustomers(Map<String, String> headersParam, Map<String, String> requestParam){
        if (!this.apiKey.equals(headersParam.get(HEADERS_X_API_KEY))) {
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }

        return customerResponse(
                PageRequest.of(
                        toNumberOrDefault(requestParam.get(PARAM_PAGE), defaultPage),
                        toNumberOrDefault(requestParam.get(PARAM_PAGE_SIZE), defaultPageSize)
                )
        );

    }

    public ResponseEntity<?> fetchCustomerById(Map<String, String> headersParam, int customerId){
        if (!this.apiKey.equals(headersParam.get(HEADERS_X_API_KEY))) {
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }

        Optional<Customer> customer = customerRepository.fetchCustomerById(customerId);

        if (customer.isEmpty()) {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(customer.get());
    }

    public ResponseEntity<?> createNewCustomer(Map<String, String> headersParam, Customer customer) {
        if (!this.apiKey.equals(headersParam.get(HEADERS_X_API_KEY))) {
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }

        if (customer == null) return new ResponseEntity<>("Empty response body", HttpStatus.BAD_REQUEST);

        if (customer.getCustomerId() == null) {
            return new ResponseEntity<>("Required customer ID", HttpStatus.BAD_REQUEST);
        }

        if (
                customer.getStoreId() == null ||
                        customer.getFirstName() == null ||
                        customer.getLastName() == null ||
                        customer.getAddressId() == null ||
                        customer.getCreateDate() == null
        ){
            return new ResponseEntity<>("Check mandatory fields", HttpStatus.BAD_REQUEST);
        }

        Optional<Customer> customerOptional = customerRepository.fetchCustomerById(Integer.valueOf(customer.getCustomerId()));

        if (customerOptional.isEmpty()) {
            customerRepository.save(customer);
            return new ResponseEntity<>(
                    "Customer ID: %s created successfully".formatted(customer.getCustomerId()),
                    HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Customer already exists", HttpStatus.CONFLICT);
    }

    public ResponseEntity<?> updateCustomer(Map<String, String> headersParam, Customer customer) {
        if (!this.apiKey.equals(headersParam.get(HEADERS_X_API_KEY))) {
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }

        if (customer == null) return new ResponseEntity<>("Empty response body", HttpStatus.BAD_REQUEST);

        if (
                customer.getCustomerId() == null ||
                        customer.getStoreId() == null ||
                        customer.getFirstName() == null ||
                        customer.getLastName() == null ||
                        customer.getAddressId() == null ||
                        customer.getCreateDate() == null
        ) {
            return new ResponseEntity<>("Missed mandatory field, please check", HttpStatus.BAD_REQUEST);
        }

        Optional<Customer> customerOptional = customerRepository.fetchCustomerById(Integer.valueOf(customer.getCustomerId()));
        if (customerOptional.isEmpty()) return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);

        customerRepository.save(customer);

        return new ResponseEntity<>("Customer updated successfully", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> deleteCustomerById(Map<String, String> headersParam, int customerId) {
        if (!this.apiKey.equals(headersParam.get(HEADERS_X_API_KEY))) {
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }

        if (customerId < 0) return new ResponseEntity<>("Incorrect customer ID", HttpStatus.BAD_REQUEST);

        Optional<Customer> customerOptional = customerRepository.fetchCustomerById(customerId);
        if (customerOptional.isEmpty()) return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);

        customerRepository.deleteById((short) customerId);

        return new ResponseEntity<>("Customer ID: %s was deleted".formatted(customerId), HttpStatus.ACCEPTED);
    }

    // ***

    private int toNumberOrDefault(String value, int defaultValue) {
        if (value == null) return defaultValue;
        if (value.isBlank()) return defaultValue;
        return Integer.parseInt(value);
    }

    private ResponseEntity<?> customerResponse(Pageable page) {
        Page<Customer> pageResponse = customerRepository.fetchAllCustomers(page);
        return ResponseEntity.ok(
                new CustomerResponse(
                        pageResponse.getPageable().getPageNumber(),
                        pageResponse.getPageable().getPageSize(),
                        pageResponse.getTotalPages(),
                        pageResponse.get().toList()
                )
        );
    }
}
