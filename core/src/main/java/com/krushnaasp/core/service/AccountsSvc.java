package com.krushnaasp.core.service;

import com.krushnaasp.core.entity.Accounts;

import java.util.List;

public interface AccountsSvc {


    List<Accounts> fetchAccountsList();

    Accounts saveAccounts(Accounts accounts);

    Accounts fetchByAccountsId(Long id);

    public void deleteAccountsById(Long id);

    Accounts updateAccountsById(Long id, Accounts accountsToBeUpdated);
}
