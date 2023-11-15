package com.example.rewardsprogram.exception;


import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    private final String errorMessage;

    public NotFoundException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }
}
