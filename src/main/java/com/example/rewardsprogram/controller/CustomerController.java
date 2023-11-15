package com.example.rewardsprogram.controller;

import com.example.rewardsprogram.model.Customer;
import com.example.rewardsprogram.model.ResponseMessage;
import com.example.rewardsprogram.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;


    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //    Read - Get all customers.
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.findAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    //    Read - Get customer by ID.
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
        Customer customer = customerService.findCustomerById(id);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/search/firstname")
    public ResponseEntity<?> getCustomersByFirstName(@RequestParam String firstName) {
        List<Customer> customers = customerService.findCustomersByFirstName(firstName);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/search/lastname")
    public ResponseEntity<?> getCustomersByLastName(@RequestParam String lastName) {
        List<Customer> customers = customerService.findCustomersByLastName(lastName);
        return ResponseEntity.ok(customers);
    }

    @PostMapping
    public ResponseEntity<Customer> createOrUpdateCustomer(@Validated @RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveOrUpdateCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteCustomer(@PathVariable("id") Long id) {
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            throw new RuntimeException("Not find customer by " + id);
        }
        customerService.deleteCustomerByCustomerId(id);
        return new ResponseEntity<>(new ResponseMessage("Customer has been deleted", customer), HttpStatus.OK);
    }
}
