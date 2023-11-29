package com.krushna.accountservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsSummary {
    private String availableFund;
    private List<ClientSummary> clientSummaries;
    private List<AccountSummary> accountSummaries;
}
