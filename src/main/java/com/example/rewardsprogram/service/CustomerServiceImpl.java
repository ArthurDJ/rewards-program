package com.example.rewardsprogram.service;


import com.example.rewardsprogram.dao.CustomerRepository;
import com.example.rewardsprogram.entity.CustomerEntity;
import com.example.rewardsprogram.model.Customer;
import com.example.rewardsprogram.util.CustomerEntityVoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.rewardsprogram.util.CustomerEntityVoConverter.convertEntityToVo;
import static com.example.rewardsprogram.util.CustomerEntityVoConverter.convertVoToEntity;


@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public Customer findCustomerById(Long customerId) {
        CustomerEntity customerEntity = customerRepository.findByCustomerId(customerId).orElse(null);
        return convertEntityToVo(customerEntity);
    }


    @Override
    public List<Customer> findCustomersByFirstName(String firstName) {
        List<CustomerEntity> customerEntities = customerRepository.findAllByFirstName(firstName);
        return customerEntities.stream().map(CustomerEntityVoConverter::convertEntityToVo).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findCustomersByLastName(String lastName) {
        List<CustomerEntity> customerEntities = customerRepository.findAllByLastName(lastName);
        return customerEntities.stream().map(CustomerEntityVoConverter::convertEntityToVo).collect(Collectors.toList());
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        CustomerEntity customerEntity = customerRepository.findByEmail(email).orElse(null);
        ;
        return convertEntityToVo(customerEntity);
    }

    @Override
    public List<Customer> findCustomersByFirstNameStartingWith(String prefix) {
        List<CustomerEntity> customerEntities = customerRepository.findAllByFirstNameStartingWith(prefix);
        return customerEntities.stream().map(CustomerEntityVoConverter::convertEntityToVo).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findCustomersByDateOgBirth(Date dateOfBirth) {
        List<CustomerEntity> customerEntities = customerRepository.findAllByDateOfBirth(dateOfBirth);
        return customerEntities.stream().map(CustomerEntityVoConverter::convertEntityToVo).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findCustomersByPhone(String Phone) {
        List<CustomerEntity> customerEntities = customerRepository.findAllByPhone(Phone);
        return customerEntities.stream().map(CustomerEntityVoConverter::convertEntityToVo).collect(Collectors.toList());
    }

    @Override
    public Customer saveOrUpdateCustomer(Customer customer) {
        CustomerEntity customerEntity = customerRepository.saveAndFlush(convertVoToEntity(customer));
        return convertEntityToVo(customerEntity);
    }

    @Override
    public void deleteCustomerByCustomerId(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<Customer> findAllCustomers() {
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        return customerEntities.stream().map(CustomerEntityVoConverter::convertEntityToVo).collect(Collectors.toList());

    }
}

