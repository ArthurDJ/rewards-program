package com.example.rewardsprogram.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


@Entity(name = "Transaction")
@Table(name = "transactions", uniqueConstraints = {
        @UniqueConstraint(columnNames = "TransactionID")
})
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID")
    private Long transactionId;


    //    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
//   it should not be fetched from the database until it's explicitly accessed in the code.
    @JoinColumn(name = "CustomerID", nullable = false)
    // This annotation establishes a foreign key relationship to the Customer entity
    private CustomerEntity customerEntity;


    @Column(name = "TransactionDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date transactionDate = new Date();

    @Column(name = "Total", nullable = false)
    private BigDecimal total;


}
