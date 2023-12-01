package com.krushna.accountservice.service;

//import com.javalearning.springbootdemo.entity.Accounts;
import com.krushna.accountservice.entity.Accounts;
import com.krushna.accountservice.entity.Interest;
import com.krushna.accountservice.entity.Investment;
import com.krushna.accountservice.model.AccountStatementTxns;

import java.util.Date;
import java.util.List;

public interface AccountsSvc {


    List<Accounts> fetchAccountsList();

    Accounts saveAccounts(Accounts accounts) throws Exception;

    List<AccountStatementTxns> accountStatement(long accountId, Date dateTillItGenerates, Long clinetID) throws Exception;

    String accountSettlement(long accountId, Date dateTillItGenerates, String fmName) throws Exception;

    Accounts fetchByAccountsId(Long id);

    public void deleteAccountsById(Long id);

    Accounts updateAccountsById(Long id, Accounts accountsToBeUpdated);

    List<Accounts> getAccountsForFMID(long fmID);
    Interest saveInterest(Interest interest) throws Exception;
    Investment saveInvestment(Investment investment) throws Exception;
    //List<AccountStatementTxns> accountStatement(long accountId, Date dateTillItGenerates) throws Exception;
}
