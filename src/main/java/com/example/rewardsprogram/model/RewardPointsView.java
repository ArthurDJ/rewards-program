package com.example.rewardsprogram.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class RewardPointsView {


    private Long transactionId;

    @NotNull(message = "Require customer ID")
    private Long customerId;

    @PastOrPresent(message = "Transaction date must be in the past or present")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transactionDate;

    @Min(0)
    @NotNull(message = "Points are required.")
    private Integer points;
}
