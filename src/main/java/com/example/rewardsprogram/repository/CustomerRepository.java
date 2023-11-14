package com.example.rewardsprogram.repository;

import com.example.rewardsprogram.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
//Used to indicate that the class provides
//the mechanism for storage, retrieval, search, update and delete operation on objects.
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Find customers by first name
    List<Customer> findAllByFirstName(String firstName);

    // Find customers by last name
    List<Customer> findAllByLastName(String lastName);

    // Find a customer by email
    Customer findByEmail(String email);

    // Find customers by a pattern in their first name (e.g., all customers whose first name starts with 'Jo')
    List<Customer> findByFirstNameStartingWith(String prefix);


    // Find customers by birthdate
    List<Customer> findByDateOfBirth(Date dateOfBirth);

    // Find customers bt Phone number
    List<Customer> findAllByPhone(String Phone);


}
