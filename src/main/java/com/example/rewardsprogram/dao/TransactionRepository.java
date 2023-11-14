package com.example.rewardsprogram.dao;

import com.example.rewardsprogram.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Find transactions by customer ID
    List<Transaction> findAllByCustomerID(Long customerID);

    // Find transactions by  transaction ID
    Transaction findByTransactionId(Long transactionID);

    // Find transactions within a date range
    List<Transaction> findALLByTransactionDateBetween(Date startDate, Date endDate);

    // Find transactions on a specific date
    List<Transaction> findAllByTransactionDate(Date transactionDate);

    // Find transactions before a specific date
    List<Transaction> findAllByTransactionDateBefore(Date beforeDate);

    // Find transactions after a specific date
    List<Transaction> findAllByTransactionDateAfter(Date afterDate);

}
