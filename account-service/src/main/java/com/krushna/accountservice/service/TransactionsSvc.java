package com.krushna.accountservice.service;

//import com.javalearning.springbootdemo.entity.Transactions;
import com.krushna.accountservice.entity.Transactions;

import java.util.List;

public interface TransactionsSvc {


    List<Transactions> fetchTransactionsList();

    Transactions saveTransactions(Transactions transactions);

    Transactions fetchByTransactionsId(Long id);

    public void deleteTransactionsById(Long id);

    Transactions updateTransactionsById(Long id, Transactions transactionsToBeUpdated);
}
