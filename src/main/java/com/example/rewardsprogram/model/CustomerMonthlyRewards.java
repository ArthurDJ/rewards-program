package com.example.rewardsprogram.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerMonthlyRewards {
    public CustomerMonthlyRewards(Long customerId) {
        this.customerId = customerId;
    }

    @NotNull
    private Long customerId;

    private Map<String, Integer> monthlyPoints = new HashMap<>();

    private Integer totalPoints = 0;

}
