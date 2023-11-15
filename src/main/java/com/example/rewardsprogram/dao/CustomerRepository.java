package com.example.rewardsprogram.dao;

import com.example.rewardsprogram.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
//Used to indicate that the class provides
//the mechanism for storage, retrieval, search, update and delete operation on objects.
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    /*
    The first parameter, Customer,
    tells Spring Data JPA that this repository is specifically for entities of type Customer.
    The second parameter, Long,
    indicates that the primary key field (@Id) of the RewardPointsView entity is of type Long.
     */


    // Find customer by customer ID
    Optional<CustomerEntity> findByCustomerId(Long customerID);

    // Find customers by first name
    List<CustomerEntity> findAllByFirstName(String firstName);

    // Find customers by last name
    List<CustomerEntity> findAllByLastName(String lastName);

    // Find a customer by email
    Optional<CustomerEntity> findByEmail(String email);

    // Find customers by a pattern in their first name (e.g., all customers whose first name starts with 'Jo')
    List<CustomerEntity> findAllByFirstNameStartingWith(String prefix);

    // Find customers by birthdate
    List<CustomerEntity> findAllByDateOfBirth(Date dateOfBirth);

    // Find customers by Phone number
    List<CustomerEntity> findAllByPhone(String Phone);

}
