//package Sakila_API.bootstrap;
//
//import Sakila_API.model.Customer;
//import Sakila_API.repository.CustomerApiRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class Bootstrap implements CommandLineRunner {
//    @Autowired
//    private CustomerApiRepository customerRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        List<Customer> list = (List<Customer>) customerRepository.findAll();
//        list.forEach(System.out::println);
//    }
//}
