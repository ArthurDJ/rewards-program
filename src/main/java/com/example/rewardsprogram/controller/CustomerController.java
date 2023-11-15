package com.example.rewardsprogram.controller;

import com.example.rewardsprogram.model.Customer;
import com.example.rewardsprogram.model.ResponseMessage;
import com.example.rewardsprogram.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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


    @GetMapping("/search/firstName")
    public ResponseEntity<List<Customer>> getCustomersByFirstName(@RequestParam String firstName) {
        List<Customer> customers = customerService.findCustomersByFirstName(firstName);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/search/lastName")
    public ResponseEntity<List<Customer>> getCustomersByLastName(@RequestParam String lastName) {
        List<Customer> customers = customerService.findCustomersByLastName(lastName);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/search/prefix")
    public ResponseEntity<List<Customer>> getCustomersByFirstNameStartingWith(@RequestParam String prefix) {
        List<Customer> customers = customerService.findCustomersByFirstNameStartingWith(prefix);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/search/dob")
    public ResponseEntity<List<Customer>> getCustomersByDateOfBirth(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth) {
        List<Customer> customers = customerService.findCustomersByDateOfBirth(dateOfBirth);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/search/phone")
    public ResponseEntity<List<Customer>> getCustomersByPhone(@RequestParam String phone) {
        List<Customer> customers = customerService.findCustomersByPhone(phone);
        return ResponseEntity.ok(customers);
    }
    @GetMapping("/search/email")
    public ResponseEntity<Customer> getCustomerByEmail(@RequestParam String email) {
        Customer customer =  customerService.findCustomerByEmail(email);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Create a new customer
    @PostMapping
    public ResponseEntity<ResponseMessage> createCustomer(@Validated @RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveOrUpdateCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessage("Customer has been created.", savedCustomer));
    }

    // Update an existing customer
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateCustomer(@PathVariable Long id, @Validated @RequestBody Customer customer) {
        Customer currentCustomer = customerService.findCustomerById(id);
        if (currentCustomer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Customer doesn't exist."));
        }
        // Set the id of the customer to ensure the correct customer is updated
        customer.setCustomerId(id);
        Customer updatedCustomer = customerService.saveOrUpdateCustomer(customer);
        return ResponseEntity.ok(new ResponseMessage("Customer updated successfully.", updatedCustomer));
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
