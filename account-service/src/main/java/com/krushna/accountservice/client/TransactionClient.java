package com.krushna.accountservice.client;

import com.krushna.accountservice.entity.FundManager;
import com.krushna.accountservice.entity.Transactions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface TransactionClient {
    @GetExchange("transactions/Transactions/{id}")
    public Transactions fetchTransactionsById(@PathVariable("id") Long id);

}
