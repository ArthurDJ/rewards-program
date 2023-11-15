package com.example.rewardsprogram.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Long customerId;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "First name is mandatory")
    private String lastName;

    @Email
    private String email;

    @PastOrPresent(message = "Create date must be in the past or present")
    @JsonFormat(pattern = "yyyy-MM-dd")
//    Ensures the date is not in the future.
    private Date createDate;

    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
//    Ensures the date is  in the past.
    private Date dateOfBirth;

    private String phone;

}
