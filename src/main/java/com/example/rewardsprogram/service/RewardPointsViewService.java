package com.example.rewardsprogram.service;

import com.example.rewardsprogram.model.RewardPointsView;

import java.util.Date;
import java.util.List;

public interface RewardPointsViewService {
    List<RewardPointsView> findRewardPointsByCustomerId(Long customerId);

    RewardPointsView findRewardPointsByTransactionId(Long transactionId);

    List<RewardPointsView> findRewardPointsByTransactionDate(Date transactionDate);

    List<RewardPointsView> findRewardPointsBetweenTransactionDates(Date startDate, Date endDate);

    List<RewardPointsView> findRewardPointsBeforeTransactionDate(Date beforeDate);

    List<RewardPointsView> findRewardPointsAfterTransactionDate(Date afterDate);

    List<RewardPointsView> findRewardPointsByPointsGreaterThanEqual(Integer points);

    List<RewardPointsView> findRewardPointsByPointsLessThanEqual(Integer points);

    List<RewardPointsView> findRewardPointsBetweenPoints(Integer startPoints, Integer endPoints);

    List<RewardPointsView> findALLRewardPoints();
}
