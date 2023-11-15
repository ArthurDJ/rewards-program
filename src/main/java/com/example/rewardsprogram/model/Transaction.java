package com.example.rewardsprogram.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private Long transactionId;

    @NotNull(message = "Require customer ID.")
    private Long customerId;


    @NotNull(message = "Require transaction Date.")
    @PastOrPresent(message = "Transaction date must be in the past or present.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transactionDate;

    @NotNull
    @DecimalMin(value = "0.01", message = "Total amount must be greater than 0.")
    private BigDecimal total;


}
