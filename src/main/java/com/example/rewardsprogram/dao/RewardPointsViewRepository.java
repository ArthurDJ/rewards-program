package com.example.rewardsprogram.dao;


import com.example.rewardsprogram.entity.RewardPointsViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardPointsViewRepository extends JpaRepository<RewardPointsViewEntity, Long> {
}
