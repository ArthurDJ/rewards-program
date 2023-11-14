package com.example.rewardsprogram.dao;

import com.example.rewardsprogram.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    // Find transactions by customer ID
    List<TransactionEntity> findAllByCustomerID(Long customerID);

    // Find transactions by  transaction ID
    Optional<TransactionEntity> findByTransactionId(Long transactionID);

    // Find transactions within a date range
    List<TransactionEntity> findALLByTransactionDateBetween(Date startDate, Date endDate);

    // Find transactions on a specific date
    List<TransactionEntity> findAllByTransactionDate(Date transactionDate);

    // Find transactions before a specific date
    List<TransactionEntity> findAllByTransactionDateBefore(Date beforeDate);

    // Find transactions after a specific date
    List<TransactionEntity> findAllByTransactionDateAfter(Date afterDate);

    // Find total great or equal a specific number
    List<TransactionEntity> findAllByTotalGreaterThanEqual(BigDecimal total);

    // Find total less or equal a specific number
    List<TransactionEntity> findAllByTotalLessThanEqual(BigDecimal total);

}
