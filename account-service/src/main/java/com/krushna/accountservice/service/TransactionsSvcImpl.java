package com.krushna.accountservice.service;

//import com.javalearning.springbootdemo.entity.Transactions;
//import com.javalearning.springbootdemo.repository.TransactionsRepo;
import com.krushna.accountservice.entity.Transactions;
import com.krushna.accountservice.repository.TransactionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransactionsSvcImpl implements TransactionsSvc{
    @Autowired
    private TransactionsRepo transactionsRepo;

    @Override
    public List<Transactions> fetchTransactionsList() {
        return transactionsRepo.findAll();
    }

    @Override
    public Transactions saveTransactions(Transactions Transactions) {
        return transactionsRepo.save(Transactions);
    }

    @Override
    public Transactions fetchByTransactionsId(Long id){
        Optional<Transactions> FMResponse= transactionsRepo.findById(id);
        return FMResponse.get();
    }
    @Override
    public void deleteTransactionsById(Long id) {
        transactionsRepo.deleteById(id);
    }

    @Override
    public Transactions updateTransactionsById(Long id, Transactions transactionsToBeUpdated) {
        Transactions existingData= transactionsRepo.findById(id).get();
        if(Objects.nonNull(transactionsToBeUpdated.getRemarks())&& !"".equalsIgnoreCase(transactionsToBeUpdated.getRemarks())){
            existingData.setRemarks(transactionsToBeUpdated.getRemarks());
        }
        if(Objects.nonNull(transactionsToBeUpdated.getFromClientId())){
            existingData.setFromClientId(transactionsToBeUpdated.getFromClientId());
        }
        if(Objects.nonNull(transactionsToBeUpdated.getToClientId())){
            existingData.setToClientId(transactionsToBeUpdated.getToClientId());
        }
        if(Objects.nonNull(transactionsToBeUpdated.getFromAccountId())){
            existingData.setFromAccountId(transactionsToBeUpdated.getFromAccountId());
        }
        if(Objects.nonNull(transactionsToBeUpdated.getToAccountId())){
            existingData.setToAccountId(transactionsToBeUpdated.getToAccountId());
        }
        if(Objects.nonNull(transactionsToBeUpdated.getDate())){
            existingData.setDate(transactionsToBeUpdated.getDate());
        }
        if(Objects.nonNull(transactionsToBeUpdated.getAmount())){
            existingData.setAmount(transactionsToBeUpdated.getAmount());
        }
        return transactionsRepo.save(existingData);
    }
}
