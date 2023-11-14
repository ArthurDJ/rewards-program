package com.example.rewardsprogram.dao;

import com.example.rewardsprogram.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    // Find transactions by customer ID
    List<TransactionEntity> findAllByCustomerID(Long customerID);

    // Find transactions by  transaction ID
    TransactionEntity findByTransactionId(Long transactionID);

    // Find transactions within a date range
    List<TransactionEntity> findALLByTransactionDateBetween(Date startDate, Date endDate);

    // Find transactions on a specific date
    List<TransactionEntity> findAllByTransactionDate(Date transactionDate);

    // Find transactions before a specific date
    List<TransactionEntity> findAllByTransactionDateBefore(Date beforeDate);

    // Find transactions after a specific date
    List<TransactionEntity> findAllByTransactionDateAfter(Date afterDate);

}
