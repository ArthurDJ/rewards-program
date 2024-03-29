package com.example.rewardsprogram.entity;


import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@Getter

@Entity(name = "RewardPointsView")
@Immutable
// Marks the entity as immutable. Hibernate will not try to write any changes to this entity.
@Table(name = "rewardPointsView", uniqueConstraints = {
        @UniqueConstraint(columnNames = "TransactionID")
})
public class RewardPointsViewEntity {


    @Id
    @Column(name = "TransactionID")
    private Long transactionId;


    @Column(name = "CustomerID")
    private Long customerId;


    @Column(name = "TransactionDate")
    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    @Column(name = "Points")
    private Integer points;
}
