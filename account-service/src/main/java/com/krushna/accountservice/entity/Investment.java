package com.krushna.accountservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date tradeDate;
    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private long Amount;
    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private long noOfUnits;
    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private long unitValueOnThisDate;
    private String tradeType;//IN or OUT
    private String stockSymbol;//Identifies the stock name
    @ManyToOne
    private Accounts accounts;
    @ManyToOne
    private Clients clients;
    @Column(name = "timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Timestamp timestamp;
}
