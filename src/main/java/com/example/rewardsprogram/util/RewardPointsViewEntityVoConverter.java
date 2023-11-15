package com.example.rewardsprogram.util;

import com.example.rewardsprogram.entity.RewardPointsViewEntity;
import com.example.rewardsprogram.model.RewardPointsView;

public class RewardPointsViewEntityVoConverter {
    public static RewardPointsView convertEntityToVo(RewardPointsViewEntity rewardPointsViewEntity) {
        if (rewardPointsViewEntity == null) {
            return null;
        }
        RewardPointsView rewardPointsViewVo = new RewardPointsView();
        rewardPointsViewVo.setTransactionId(rewardPointsViewEntity.getTransactionId());
        rewardPointsViewVo.setCustomerId(rewardPointsViewEntity.getCustomerId());
        rewardPointsViewVo.setTransactionDate(rewardPointsViewEntity.getTransactionDate());
        rewardPointsViewVo.setPoints(rewardPointsViewEntity.getPoints());
        return rewardPointsViewVo;
    }
}
