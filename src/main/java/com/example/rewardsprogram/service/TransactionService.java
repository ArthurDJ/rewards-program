package com.example.rewardsprogram.service;

import com.example.rewardsprogram.model.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TransactionService {
    List<Transaction> findAllTransactions();

    Transaction findTransactionById(Long transactionId);

    List<Transaction> findTransactionsByCustomerId(Long customerId);

    List<Transaction> findTransactionsByDate(Date transactionDate);

    List<Transaction> findTransactionsBetweenDates(Date startDate, Date endDate);

    List<Transaction> findTransactionsBeforeDate(Date beforeDate);

    List<Transaction> findTransactionsAfterDate(Date afterDate);

    List<Transaction> findTransactionsWithTotalGreaterThanEqual(BigDecimal total);

    List<Transaction> findTransactionsWithTotalLessThanEqual(BigDecimal total);

    Transaction saveOrUpdateTransaction(Transaction transaction);

    void deleteTransactionById(Long transactionId);


}
