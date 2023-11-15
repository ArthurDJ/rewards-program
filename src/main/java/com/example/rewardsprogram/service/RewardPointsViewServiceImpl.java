package com.example.rewardsprogram.service;

import com.example.rewardsprogram.dao.RewardPointsViewRepository;
import com.example.rewardsprogram.entity.RewardPointsViewEntity;
import com.example.rewardsprogram.model.RewardPointsView;
import com.example.rewardsprogram.util.RewardPointsViewEntityVoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("rewardPointsService")
public class RewardPointsViewServiceImpl implements RewardPointsViewService {
    @Autowired
    private RewardPointsViewRepository rewardPointsViewRepository;

    @Override
    public List<RewardPointsView> findRewardPointsByCustomerId(Long customerId) {
        List<RewardPointsViewEntity> entities = rewardPointsViewRepository.findAllByCustomerId(customerId);
        return entities.stream()
                .map(RewardPointsViewEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public RewardPointsView findRewardPointsByTransactionId(Long transactionId) {
        return rewardPointsViewRepository.findByTransactionId(transactionId)
                .map(RewardPointsViewEntityVoConverter::convertEntityToVo)
                .orElse(null);
    }

    @Override
    public List<RewardPointsView> findRewardPointsByTransactionDate(Date transactionDate) {
        List<RewardPointsViewEntity> entities = rewardPointsViewRepository.findByTransactionDate(transactionDate);
        return entities.stream()
                .map(RewardPointsViewEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public List<RewardPointsView> findRewardPointsBetweenTransactionDates(Date startDate, Date endDate) {
        List<RewardPointsViewEntity> entities = rewardPointsViewRepository.findALLByTransactionDateBetween(startDate, endDate);
        return entities.stream()
                .map(RewardPointsViewEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public List<RewardPointsView> findRewardPointsBeforeTransactionDate(Date beforeDate) {
        List<RewardPointsViewEntity> entities = rewardPointsViewRepository.findAllByTransactionDateBefore(beforeDate);
        return entities.stream()
                .map(RewardPointsViewEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public List<RewardPointsView> findRewardPointsAfterTransactionDate(Date afterDate) {
        List<RewardPointsViewEntity> entities = rewardPointsViewRepository.findAllByTransactionDateAfter(afterDate);
        return entities.stream()
                .map(RewardPointsViewEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public List<RewardPointsView> findRewardPointsByPointsGreaterThanEqual(Integer points) {
        List<RewardPointsViewEntity> entities = rewardPointsViewRepository.findAllByPointsGreaterThanEqual(points);
        return entities.stream()
                .map(RewardPointsViewEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public List<RewardPointsView> findRewardPointsByPointsLessThanEqual(Integer points) {
        List<RewardPointsViewEntity> entities = rewardPointsViewRepository.findAllByPointsLessThanEqual(points);
        return entities.stream()
                .map(RewardPointsViewEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public List<RewardPointsView> findRewardPointsBetweenPoints(Integer startPoints, Integer endPoints) {
        List<RewardPointsViewEntity> entities = rewardPointsViewRepository.findAllByPointsBetween(startPoints, endPoints);
        return entities.stream()
                .map(RewardPointsViewEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }
}
