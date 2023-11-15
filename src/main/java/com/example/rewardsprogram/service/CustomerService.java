package com.example.rewardsprogram.service;


import com.example.rewardsprogram.model.Customer;

import java.util.Date;
import java.util.List;

public interface CustomerService {

    Customer findCustomerById(Long customerId);

    List<Customer> findCustomersByFirstName(String firstName);

    List<Customer> findCustomersByLastName(String lastName);

    Customer findCustomerByEmail(String email);

    List<Customer> findCustomersByFirstNameStartingWith(String prefix);

    List<Customer> findCustomersByDateOfBirth(Date dateOfBirth);

    List<Customer> findCustomersByPhone(String Phone);

    Customer saveOrUpdateCustomer(Customer customer);

    void deleteCustomerByCustomerId(Long customerId);

    List<Customer> findAllCustomers();



}
