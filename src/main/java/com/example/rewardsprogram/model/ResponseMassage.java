package com.example.rewardsprogram.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMassage {
    private String message;
    @JsonProperty("data")
    private Object body;

    public ResponseMassage(String message) {
        this.message = message;
    }
}
