package com.example.rewardsprogram.util;


import com.example.rewardsprogram.entity.CustomerEntity;
import com.example.rewardsprogram.model.Customer;


public class CustomerEntityVoConverter {
    public static Customer convertEntityToVo(CustomerEntity customerEntity) {
        if (customerEntity == null) {
            return null;
        }
        Customer customerVo = new Customer();
        customerVo.setCustomerId(customerEntity.getCustomerId());
        customerVo.setFirstName(customerEntity.getFirstName());
        customerVo.setLastName(customerEntity.getLastName());
        customerVo.setCreateDate(customerEntity.getCreateDate());
        customerVo.setDateOfBirth(customerEntity.getDateOfBirth());
        customerVo.setEmail(customerEntity.getEmail());
        customerVo.setPhone(customerEntity.getPhone());

        return customerVo;
    }

    public static CustomerEntity convertVoToEntity(Customer customerVo){
        if (customerVo == null) {
            return null;
        }
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerId(customerVo.getCustomerId());
        customerEntity.setFirstName(customerVo.getFirstName());
        customerEntity.setLastName(customerVo.getLastName());
        customerEntity.setCreateDate(customerVo.getCreateDate());
        customerEntity.setDateOfBirth(customerVo.getDateOfBirth());
        customerEntity.setEmail(customerVo.getEmail());
        customerEntity.setPhone(customerVo.getPhone());
        return customerEntity;
    }

}
