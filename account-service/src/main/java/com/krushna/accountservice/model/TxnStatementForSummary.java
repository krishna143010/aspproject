package com.krushna.accountservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.Date;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TxnStatementForSummary {
    Long accountID;
    Long clientID;
    Long amount;
    Long FM;
    private Date date;
}
