package com.example.rewardsprogram.controller;


import com.example.rewardsprogram.dao.RewardPointsViewRepository;
import com.example.rewardsprogram.model.CustomerMonthlyRewards;
import com.example.rewardsprogram.model.RewardPointsView;
import com.example.rewardsprogram.model.Transaction;
import com.example.rewardsprogram.service.RewardPointsViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/rewardPoints")
public class RewardPointsViewController {
    private final RewardPointsViewService rewardPointsViewService;

    @Autowired
    public RewardPointsViewController(RewardPointsViewService rewardPointsViewService) {
        this.rewardPointsViewService = rewardPointsViewService;
    }

    @GetMapping
    public ResponseEntity<List<RewardPointsView>> getRewardPoints() {
        List<RewardPointsView> rewardPointsViews = rewardPointsViewService.findALLRewardPoints();
        return ResponseEntity.ok(rewardPointsViews);
    }

    // 获取特定客户的奖励点
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<RewardPointsView>> getRewardPointsByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(rewardPointsViewService.findRewardPointsByCustomerId(customerId));
    }

    // 获取特定交易的奖励点
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<RewardPointsView> getRewardPointsByTransactionId(@PathVariable Long transactionId) {
        return ResponseEntity.ok(rewardPointsViewService.findRewardPointsByTransactionId(transactionId));
    }

    // 根据交易日期获取奖励点
    @GetMapping("/date")
    public ResponseEntity<List<RewardPointsView>> getRewardPointsByTransactionDate(@RequestParam Date transactionDate) {
        return ResponseEntity.ok(rewardPointsViewService.findRewardPointsByTransactionDate(transactionDate));
    }

    // 获取两个日期之间的奖励点
    @GetMapping("/dateBetween")
    public ResponseEntity<List<RewardPointsView>> getRewardPointsBetweenTransactionDates(
            @RequestParam(required = false, defaultValue = "0001-01-01")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false, defaultValue = "9999-12-31")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return ResponseEntity.ok(rewardPointsViewService.findRewardPointsBetweenTransactionDates(startDate, endDate));
    }

    // 获取某一日期之前的奖励点
    @GetMapping("/beforeDate")
    public ResponseEntity<List<RewardPointsView>> getRewardPointsBeforeTransactionDate(@RequestParam Date beforeDate) {
        return ResponseEntity.ok(rewardPointsViewService.findRewardPointsBeforeTransactionDate(beforeDate));
    }

    // 获取某一日期之后的奖励点
    @GetMapping("/afterDate")
    public ResponseEntity<List<RewardPointsView>> getRewardPointsAfterTransactionDate(@RequestParam Date afterDate) {
        return ResponseEntity.ok(rewardPointsViewService.findRewardPointsAfterTransactionDate(afterDate));
    }

    // 获取等于或大于特定积分的奖励点
    @GetMapping("/pointsGreaterEqual")
    public ResponseEntity<List<RewardPointsView>> getRewardPointsByPointsGreaterThanEqual(@RequestParam Integer points) {
        return ResponseEntity.ok(rewardPointsViewService.findRewardPointsByPointsGreaterThanEqual(points));
    }

    // 获取等于或小于特定积分的奖励点
    @GetMapping("/pointsLessEqual")
    public ResponseEntity<List<RewardPointsView>> getRewardPointsByPointsLessThanEqual(@RequestParam Integer points) {
        return ResponseEntity.ok(rewardPointsViewService.findRewardPointsByPointsLessThanEqual(points));
    }

    // 获取特定积分范围内的奖励点
    @GetMapping("/points")
    public ResponseEntity<List<RewardPointsView>> getRewardPointsBetweenPoints(
            @RequestParam(required = false, defaultValue = "0") Integer startPoints,
            @RequestParam(required = false, defaultValue = "1000000000") Integer endPoints) {
        return ResponseEntity.ok(rewardPointsViewService.findRewardPointsBetweenPoints(startPoints, endPoints));
    }

    @GetMapping("/monthlyRewards")
    public ResponseEntity<List<CustomerMonthlyRewards>> getMonthlyRewards(
            @RequestParam(required = false, defaultValue = "3") int months,
            @RequestParam(required = false) Long customerId) {
        if (customerId == null) {
            return ResponseEntity.ok(rewardPointsViewService.calculateMonthlyRewardsForAllCustomers(months));
        } else {
            return ResponseEntity.ok(rewardPointsViewService.calculateMonthlyRewardsForCustomer(customerId, months));
        }
    }

}
