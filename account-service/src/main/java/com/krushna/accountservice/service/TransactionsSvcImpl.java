package com.krushna.accountservice.service;

//import com.javalearning.springbootdemo.entity.Transactions;
//import com.javalearning.springbootdemo.repository.TransactionsRepo;
import com.krushna.accountservice.entity.Accounts;
import com.krushna.accountservice.entity.Clients;
import com.krushna.accountservice.entity.FundManager;
import com.krushna.accountservice.entity.Transactions;
import com.krushna.accountservice.model.AccountSummary;
import com.krushna.accountservice.model.ClientSummary;
import com.krushna.accountservice.model.TransactionsSummary;
import com.krushna.accountservice.model.TxnStatementForSummary;
import com.krushna.accountservice.repository.AccountsRepo;
import com.krushna.accountservice.repository.ClientsRepo;
import com.krushna.accountservice.repository.FundManagerRepo;
import com.krushna.accountservice.repository.TransactionsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionsSvcImpl implements TransactionsSvc{
    @Autowired
    private FundManagerRepo fundManagerRepo;
    @Autowired
    private ClientsRepo clientsRepo;
    @Autowired
    private AccountsRepo accountsRepo;
    @Autowired
    private TransactionsRepo transactionsRepo;

    @Override
    public List<Transactions> fetchTransactionsList() {
        return transactionsRepo.findAll();
    }

    @Override
    public Transactions saveTransactions(Transactions Transactions, String fmName) throws Exception {
            checkTransactionValidity(Transactions);
            Transactions.setFmid(fundManagerRepo.findByfmName(fmName));
            return transactionsRepo.save(Transactions);


    }
    private boolean checkTransactionValidity(Transactions transactions) throws Exception {
        if(transactions.getFromClientId().getClientName().equalsIgnoreCase("External")&&transactions.getToClientId().getClientName().equalsIgnoreCase("External")){
            throw new Exception("From and To cannot be External");
        } else if (transactions.getFromAccountId().getAccountName().equalsIgnoreCase("External")&&transactions.getToAccountId().getAccountName().equalsIgnoreCase("Investment")) {
            throw new Exception("You cannot directly invest from outside");
        }else if (transactions.getFromAccountId().getAccountName().equalsIgnoreCase("Investment")&&transactions.getToAccountId().getAccountName().equalsIgnoreCase("External")) {
            throw new Exception("You can only withdraw to existing accounts");
        }
        return true;
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

    @Override
    public TransactionsSummary generateTxnSummary(Long id) {
        // Using enhanced for loop(for-each) for iteration
        FundManager fmForSummary= fundManagerRepo.findById(id).get();
        String totalFund=Long.toString(getAvlFundForFM(fmForSummary));
        //return TransactionsSummary.builder().availableFund(totalFund).build();
        List<AccountSummary> accountSummaryList = generateAccountSummaryForFM(id);

        List<ClientSummary> clientSummaryList = generateClientSummaryForFM(fmForSummary);
        return TransactionsSummary.builder().availableFund(totalFund).clientSummaries(clientSummaryList).accountSummaries(accountSummaryList).build();
    }

    @Override
    public List<TxnStatementForSummary> txnStatementSummaryList() {
        List<TxnStatementForSummary> txnStatementForSummaryList = new ArrayList<>();
        for (Object result : transactionsRepo.txnStatementForSummary()) {
            if (result instanceof Object[]) {
                Object[] row = (Object[]) result;
                Long accountID = (Long) row[0];
                Long clientID = (Long) row[1];
                Long amount = (Long) row[2];
                Long FM = (Long) row[3];
                Date date=(Date) row[4];
                txnStatementForSummaryList.add(TxnStatementForSummary.builder().accountID(accountID).clientID(clientID).amount(amount).FM(FM).date(date).build());
            } else {
                // Handle unexpected result type
                log.info("Unexpected result type: " + result.getClass());
            }
        }
        return txnStatementForSummaryList;
    }

    public List<AccountSummary> generateAccountSummaryForFM(Long fmID) {
        List<Accounts> accountsAll=accountsRepo.findAll().stream()
                .filter(account -> account.getClients().getFundManager().getFmid() == fmID).collect(Collectors.toList());
        // Using enhanced for loop(for-each) for iteration
        List<AccountSummary> accountSummaryList = new ArrayList<>();
        for (Accounts account : accountsAll) {
            String netAccountBal=Long.toString(transactionsRepo.getAccountSummary(account.getAccountId(),fmID));

            System.out.println("netAccountBal: "+netAccountBal);
            accountSummaryList.add(AccountSummary.builder().accountName(account.getAccountName()).availableFund(netAccountBal).build());
        }
        return accountSummaryList;
    }
    public List<ClientSummary> generateClientSummaryForFM(FundManager fundManager) {
        List<Clients> clients=clientsRepo.findAllByfundManager(fundManager);
        // Using enhanced for loop(for-each) for iteration
        List<ClientSummary> clientSummaryList = new ArrayList<>();
        for (Clients client : clients) {
            System.out.println("Clients: "+client);
            String clientFund=Long.toString(transactionsRepo.getClientSummary(client.getClientId(),fundManager.getFmid()));
            clientSummaryList.add(ClientSummary.builder().clientName(client.getClientName()).availableFund(clientFund).build());
        }
        return clientSummaryList;
    }
    public long getAvlFundForFM(FundManager fundManager) {
        return transactionsRepo.getTotalFundSummary(fundManager.getFmid());
    }
}
