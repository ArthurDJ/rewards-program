package com.example.rewardsprogram.service;

import com.example.rewardsprogram.dao.RewardPointsViewRepository;
import com.example.rewardsprogram.entity.RewardPointsViewEntity;
import com.example.rewardsprogram.model.CustomerMonthlyRewards;
import com.example.rewardsprogram.model.RewardPointsView;
import com.example.rewardsprogram.util.RewardPointsViewEntityVoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
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

    @Override
    public List<RewardPointsView> findALLRewardPoints() {
        List<RewardPointsViewEntity> entities = rewardPointsViewRepository.findAll();
        return entities.stream()
                .map(RewardPointsViewEntityVoConverter::convertEntityToVo)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerMonthlyRewards> calculateMonthlyRewardsForAllCustomers(Integer months) {
        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.MONTH, -months+1);
        Date startDate = calendar.getTime();

        List<RewardPointsViewEntity> rewardPoints = rewardPointsViewRepository
                .findALLByTransactionDateBetween(startDate, endDate);

        Map<Long, CustomerMonthlyRewards> rewardsMap = new HashMap<>();

        for (RewardPointsViewEntity entity : rewardPoints) {
            Long customerId = entity.getCustomerId();
            Date transactionDate = entity.getTransactionDate();
            Integer points = entity.getPoints();

            SimpleDateFormat formatter = new SimpleDateFormat("MM");
            String month = formatter.format(transactionDate);

            CustomerMonthlyRewards customerRewards = rewardsMap.getOrDefault(customerId,
                    new CustomerMonthlyRewards(customerId));
            customerRewards.getMonthlyPoints().merge(month, points, Integer::sum);
            customerRewards.setTotalPoints(customerRewards.getTotalPoints() + points);

            rewardsMap.put(customerId, customerRewards);
        }

        return new ArrayList<>(rewardsMap.values());
    }

    @Override
    public List<CustomerMonthlyRewards> calculateMonthlyRewardsForCustomer(Long customerId, Integer months) {
        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.MONTH, -months+1);
        Date startDate = calendar.getTime();

        // 仅查询特定客户ID的记录
        List<RewardPointsViewEntity> rewardPoints =
                rewardPointsViewRepository.findAllByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);

        // 初始化CustomerMonthlyRewards对象
        CustomerMonthlyRewards customerRewards = new CustomerMonthlyRewards(customerId);

        // 分组并计算每个月的积分
        for (RewardPointsViewEntity entity : rewardPoints) {
            Date transactionDate = entity.getTransactionDate();
            Integer points = entity.getPoints();

            SimpleDateFormat formatter = new SimpleDateFormat("MM");
            String month = formatter.format(transactionDate);

            customerRewards.getMonthlyPoints().merge(month, points, Integer::sum);
            customerRewards.setTotalPoints(customerRewards.getTotalPoints() + points);
        }

        return Collections.singletonList(customerRewards);
    }


}
