package com.example.rewardsprogram.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "Customer")
// Tells Hibernate to make a table out of this class.
@Table(name = "customers", uniqueConstraints = {
        @UniqueConstraint(columnNames = "CustomerID"),
        @UniqueConstraint(columnNames = "email"
        )})
// This matches actual table name in the database.
public class CustomerEntity {
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
    @Column(name = "Email", nullable = false, unique = true)
    private String email;


    @Column(name = "CreateDate", nullable = false, updatable = true)
    @Temporal(TemporalType.DATE)
//    Specifies the type of the date field
    private Date createDate;

    @PrePersist
    protected void onCreate() {
        createDate = new Date();
    }


    @Column(name = "DateOfBirth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;


    @Column(name = "Phone")
    private String phone;


    @OneToMany(mappedBy = "customerEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
/*
    Cascade Types
    The cascade attribute determines which operations are cascaded from the parent to the child.
    CascadeType.ALL means all operations, including persisting, updating, and deleting, are cascaded.
    Orphan Removal
    The orphanRemoval attribute is a convenience feature that automatically removes child entities
    when they are no longer referenced from the parent side of an association.
*/
    private List<TransactionEntity> transactionEntities = new ArrayList<>();

    public void addTransaction(TransactionEntity transactionEntity) {
        transactionEntities.add(transactionEntity);
        transactionEntity.setCustomerEntity(this);
    }

    // Add a helper method to disassociate a transaction
    public void removeTransaction(TransactionEntity transactionEntity) {
        transactionEntities.remove(transactionEntity);
        transactionEntity.setCustomerEntity(null);
    }

}
