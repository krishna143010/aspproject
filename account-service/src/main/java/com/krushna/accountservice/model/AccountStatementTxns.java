package com.krushna.accountservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatementTxns {
    private Long amount;
    private Long account;
    private Date date;
    private Double interestRate;
    private Double accumulatedAmountForInterestCalc;
    private Double accumulatedInterest;
}
