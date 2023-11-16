package com.example.rewardsprogram.exception;


import lombok.Getter;

@Getter
public class NumberCantNegativeException extends RuntimeException {

    private String errorMessage;

    public NumberCantNegativeException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

}
