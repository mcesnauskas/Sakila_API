package Sakila_API.controller;

import Sakila_API.service.CustomerApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static Sakila_API.service.CustomerApiService.HEADERS_X_API_KEY;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Controller
@RequestMapping(path = "/customers")
public class CustomerApiController {
    @Autowired
    private CustomerApiService customerService;

    // http://localhost:8078/customers/all
    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllCustomers(
            @RequestHeader(value = "X-Api-Key") String apiKey,
            @RequestHeader(value = "Content-Type", defaultValue = "application/json") String content,
            @RequestParam(required = false) Map<String, String> requestParam
    ){
        return customerService.fetchAllCustomers(
                Map.of(
                        HEADERS_X_API_KEY, apiKey,
                        CONTENT_TYPE, content
                ),
                requestParam
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCustomerById(
            @RequestHeader(value = "X-Api-Key") String apiKey,
            @RequestHeader(value = "Content-Type", defaultValue = "application/json") String content,
            @PathVariable(name = "id") int customerId
    ) {
        return customerService.fetchCustomerById(
                Map.of(
                        HEADERS_X_API_KEY, apiKey,
                        CONTENT_TYPE, content
                ),
                customerId
        );
    }


}
