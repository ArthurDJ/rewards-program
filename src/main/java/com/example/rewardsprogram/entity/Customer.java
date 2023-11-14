package com.example.rewardsprogram.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "Customer")
// Tells Hibernate to make a table out of this class.
@Table(name = "Customers", uniqueConstraints = {@UniqueConstraint(columnNames = "CustomerID")})
// This matches actual table name in the database.
class Customer {
    @Id
//    Marks this field as primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Configures the way of increment of the specified column(field).
    @Column(name = "CustomerID")
//    The name attribute in the @Column annotation specifies the name of the column
//    in the database table that the entity field should be mapped to.
//    If the name attribute is omitted, the JPA provider will assume that the column name is the same as the field name.
    private Long customerId;

    @NotBlank(message = "First name is mandatory")
//    Ensure that String not null and not just whitespace.
//    Used to define a custom error message that will be returned when the validation constraint is violated.
    @Column(name = "FirstName", nullable = false)
//    Marks the column as not nullable
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Column(name = "LastName", nullable = false)
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(name = "Email", nullable = false)
    private String email;


    @Column(name = "CreateDate", nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
//    Specifies the type of the date field
    private Date createDate = new Date();


    @Column(name = "DateOfBirth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;


    @Column(name = "Phone")
    private String phone;
}
