package com.example.rewardsprogram.service;

import com.example.rewardsprogram.dao.CustomerRepository;
import com.example.rewardsprogram.dao.TransactionRepository;

import com.example.rewardsprogram.entity.CustomerEntity;
import com.example.rewardsprogram.entity.TransactionEntity;
import com.example.rewardsprogram.model.Transaction;
import com.example.rewardsprogram.util.TransactionEntityVoConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import static com.example.rewardsprogram.util.TransactionEntityVoConverter.convertEntityToVo;
import static com.example.rewardsprogram.util.TransactionEntityVoConverter.convertVoToEntity;


@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(TransactionEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public Transaction findTransactionById(Long transactionId) {
        TransactionEntity transactionEntity = transactionRepository.findById(transactionId).orElse(null);
        return convertEntityToVo(transactionEntity);
    }

    @Override
    public List<Transaction> findTransactionsByCustomerId(Long customerId) {

        return transactionRepository.findAllByCustomerEntity_CustomerId(customerId)
                .stream()
                .map(TransactionEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findTransactionsByDate(Date transactionDate) {
        return transactionRepository.findAllByTransactionDate(transactionDate)
                .stream()
                .map(TransactionEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findTransactionsBetweenDates(Date startDate, Date endDate) {
        return transactionRepository.findALLByTransactionDateBetween(startDate, endDate)
                .stream()
                .map(TransactionEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findTransactionsBeforeDate(Date beforeDate) {
        return transactionRepository.findAllByTransactionDateBefore(beforeDate)
                .stream()
                .map(TransactionEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findTransactionsAfterDate(Date afterDate) {
        return transactionRepository.findAllByTransactionDateAfter(afterDate)
                .stream()
                .map(TransactionEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findTransactionsWithTotalGreaterThanEqual(BigDecimal total) {
        return transactionRepository.findAllByTotalGreaterThanEqual(total)
                .stream()
                .map(TransactionEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findTransactionsWithTotalLessThanEqual(BigDecimal total) {
        return transactionRepository.findAllByTotalLessThanEqual(total)
                .stream()
                .map(TransactionEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

//    @Override
//    public Transaction saveOrUpdateTransaction(Transaction transaction) {
//        TransactionEntity transactionEntity = transactionRepository.saveAndFlush(convertVoToEntity(transaction));
//        return convertEntityToVo(transactionEntity);
//    }

    public Transaction saveOrUpdateTransaction(Transaction transaction) {
        TransactionEntity transactionEntity;
        if (transaction.getTransactionId() != null) {
            // Update
            transactionEntity = transactionRepository.findById(transaction.getTransactionId())
                    .orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + transaction.getTransactionId()));
            transactionEntity.setTransactionDate(transaction.getTransactionDate());
        } else {
            // Save
            transactionEntity = new TransactionEntity();
        }

        // Find CustomerEntity by CustomerId and set new customerEntity
        CustomerEntity customerEntity = customerRepository.findById(transaction.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + transaction.getCustomerId()));
        transactionEntity.setCustomerEntity(customerEntity);
        transactionEntity.setTotal(transaction.getTotal());

        TransactionEntity savedTransactionEntity = transactionRepository.save(transactionEntity);

        return TransactionEntityVoConverter.convertEntityToVo(savedTransactionEntity);
    }


    @Override
    public void deleteTransactionById(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

}
