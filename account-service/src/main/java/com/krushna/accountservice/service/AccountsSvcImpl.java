package com.krushna.accountservice.service;

//import com.javalearning.springbootdemo.entity.Accounts;
//import com.javalearning.springbootdemo.repository.AccountsRepo;
import com.krushna.accountservice.entity.Accounts;
import com.krushna.accountservice.repository.AccountsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountsSvcImpl implements AccountsSvc{
    @Autowired
    private AccountsRepo accountsRepo;

    @Override
    public List<Accounts> fetchAccountsList() {
        return accountsRepo.findAll();
    }

    @Override
    public Accounts saveAccounts(Accounts Accounts) {
        return accountsRepo.save(Accounts);
    }

    @Override
    public Accounts fetchByAccountsId(Long id){
        Optional<Accounts> FMResponse= accountsRepo.findById(id);
        return FMResponse.get();
    }
    @Override
    public void deleteAccountsById(Long id) {
        accountsRepo.deleteById(id);
    }

    @Override
    public Accounts updateAccountsById(Long id, Accounts accountsToBeUpdated) {
        Accounts existingData= accountsRepo.findById(id).get();
        if(Objects.nonNull(accountsToBeUpdated.getAccountName())&& !"".equalsIgnoreCase(accountsToBeUpdated.getAccountName())){
            existingData.setAccountName(accountsToBeUpdated.getAccountName());
        }
        if(Objects.nonNull(accountsToBeUpdated.getAccountNumber())&& !"".equalsIgnoreCase(accountsToBeUpdated.getAccountNumber())){
            existingData.setAccountNumber(accountsToBeUpdated.getAccountNumber());
        }
        if(Objects.nonNull(accountsToBeUpdated.getUpiId())&& !"".equalsIgnoreCase(accountsToBeUpdated.getUpiId())){
            existingData.setUpiId(accountsToBeUpdated.getUpiId());
        }
        if(Objects.nonNull(accountsToBeUpdated.getStatus())&& !"".equalsIgnoreCase(accountsToBeUpdated.getStatus())){
            existingData.setStatus(accountsToBeUpdated.getStatus());
        }
        if(Objects.nonNull(accountsToBeUpdated.getClients())){
            existingData.setClients(accountsToBeUpdated.getClients());
        }

        return accountsRepo.save(existingData);
    }
}
