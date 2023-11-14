package com.example.rewardsprogram.entity;


import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@Getter

@Entity(name = "RewardPoint")
@Immutable
// Marks the entity as immutable. Hibernate will not try to write any changes to this entity.
@Table(name = "RewardPointsView", uniqueConstraints = {
        @UniqueConstraint(columnNames = "TransactionID")
})
public class RewardPointsView {


    @Id
    @Column(name = "TransactionID")
    private Long transactionID;


    @Column(name = "CustomerID")
    private Long customerID;


    @Column(name = "TransactionDate")
    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    @Column(name = "Points")
    private Integer points;
}
