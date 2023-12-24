package com.example.rest;

import com.example.entities.Customer;
import com.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable Integer id) {
        Customer customer = customerService.getCustomer(id);

        if (customer == null) {
            throw new CustomerNotFoundException(
                    "Customer with id = " + id + " not found");
        }
        return customer;
    }

    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody Customer customer) {
        customer.setId(null);
        customerService.saveCustomer(customer);
        return customer;
    }

    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
        return customer;
    }

    @DeleteMapping("/customers/{id}")
    public String deleteCustomer(@PathVariable Integer id) {
        Customer customer = customerService.getCustomer(id);
        if (customer == null) {
            throw new CustomerNotFoundException(
                    "Customer with id = " + id + " not found");
        }
        customerService.deleteCustomer(id);
        return "Customer with id = " + id + " has been deleted!";
    }
}
