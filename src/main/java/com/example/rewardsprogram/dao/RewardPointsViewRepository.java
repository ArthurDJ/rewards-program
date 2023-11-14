package com.example.rewardsprogram.dao;


import com.example.rewardsprogram.entity.RewardPointsViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RewardPointsViewRepository extends JpaRepository<RewardPointsViewEntity, Long> {
    List<RewardPointsViewEntity> findAllByCustomerId(Long customerId);

    Optional<RewardPointsViewEntity> findByTransactionId(Long transactionId);

    List<RewardPointsViewEntity> findByTransactionDate(Date transactionDate);

    // Find rewardPointsView within a date range
    List<RewardPointsViewEntity> findALLByTransactionDateBetween(Date startDate, Date endDate);

    // Find rewardPointsView on a specific date
    List<RewardPointsViewEntity> findAllByTransactionDate(Date transactionDate);

    // Find rewardPointsView before a specific date
    List<RewardPointsViewEntity> findAllByTransactionDateBefore(Date beforeDate);

    // Find rewardPointsView after a specific date
    List<RewardPointsViewEntity> findAllByTransactionDateAfter(Date afterDate);

    // Find points great or equal a specific number
    List<RewardPointsViewEntity> findAllByPointsGreaterThanEqual(Integer points);

    // Find points less or equal a specific number
    List<RewardPointsViewEntity> findAllByPointsLessThanEqual(Integer points);

    //    Find rewardPointsView within a point range
    List<RewardPointsViewEntity> findAllByPointsBetween(Integer startPoints, Integer endPoints);

}
