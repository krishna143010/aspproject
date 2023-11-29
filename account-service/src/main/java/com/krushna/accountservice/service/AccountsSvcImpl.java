package com.krushna.accountservice.service;

//import com.javalearning.springbootdemo.entity.Accounts;
//import com.javalearning.springbootdemo.repository.AccountsRepo;
import com.krushna.accountservice.entity.Accounts;
import com.krushna.accountservice.entity.Interest;
import com.krushna.accountservice.entity.Investment;
import com.krushna.accountservice.entity.Transactions;
import com.krushna.accountservice.model.AccountStatementTxns;
import com.krushna.accountservice.repository.AccountsRepo;
import com.krushna.accountservice.repository.InterestRepo;
import com.krushna.accountservice.repository.InvestmentRepo;
import com.krushna.accountservice.repository.TransactionsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
@Slf4j
@Service
public class AccountsSvcImpl implements AccountsSvc{
    @Autowired
    private AccountsRepo accountsRepo;
    @Autowired
    private InterestRepo interestRepo;
    @Autowired
    private InvestmentRepo investmentRepo;
    @Autowired
    private TransactionsRepo transactionsRepo;

    @Override
    public List<Accounts> fetchAccountsList() {
        return accountsRepo.findAll();
    }

    @Override
    public Accounts saveAccounts(Accounts Accounts) throws Exception {
        if(Accounts.getAccountName().equalsIgnoreCase("External") || Accounts.getAccountName().equalsIgnoreCase("Investment")){
            throw new Exception("Adding Account Name as External/Investment is not allowed");
        }
        return accountsRepo.save(Accounts);
    }
    @Override
    public List<AccountStatementTxns> accountStatement(long accountId, Date dateTillItGenerates) throws Exception{
        List<AccountStatementTxns> accountStatementTxnList=new ArrayList<>();
        /*Long accumulatedAmountForInterestCalc= Long.valueOf(0);
        Date dateForNoOfDays = null;*/
        AccountStatementTxns prevRecord=null;
        AccountStatementTxns accountStatementTxns=null;
        boolean isFirstIteration = true;
        for (Object result : accountsRepo.getAccountStatement(accountId,dateTillItGenerates)) {
            if (result instanceof Object[]) {
                Object[] row = (Object[]) result;

                // Assuming the order of elements in the array matches the order of fields in AccountStatementTxns
                Long amount = (Long) row[0];
                Long account = (Long) row[1];
                Date resultDate = (Date) row[2];
                Double interestRate = (Double) row[3];
                if (isFirstIteration) {
                    // Perform action only on the first iteration
                    //System.out.println("First iteration action");
                    accountStatementTxns=new AccountStatementTxns(amount, account, resultDate, interestRate, (double) 0, (double) 0);
                    isFirstIteration = false; // Set the flag to false after the first iteration
                    accountStatementTxnList.add(accountStatementTxns);
                    prevRecord=accountStatementTxns;
                }else {
                    long diffInMillies = Math.abs(resultDate.getTime() - prevRecord.getDate().getTime());
                    long days= TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    double interest= ((prevRecord.getInterestRate()/(100*365))*days* prevRecord.getAccumulatedAmountForInterestCalc())+ prevRecord.getAccumulatedInterest();
                    interest = Math.round(interest * 100.0) / 100.0;

                    log.info("No of days: "+days+" Interest Earned: "+interest+" Interest Rate: "+interestRate+" Amount: "+amount+" accumForNextTime: "+(prevRecord.getAccumulatedAmountForInterestCalc()+amount));
                    accountStatementTxns=new AccountStatementTxns(amount, account, resultDate, interestRate,prevRecord.getAccumulatedAmountForInterestCalc()+amount, interest);
                    accountStatementTxnList.add(accountStatementTxns);
                    prevRecord=accountStatementTxns;
                }

                // Create an instance of AccountStatementTxns

                // Now you can work with the accountStatement object
                //logger.info("Statement txn:"+accountStatement.toString());
            } else {
                // Handle unexpected result type
                log.info("Unexpected result type: " + result.getClass());
            }
        }
        return accountStatementTxnList;
    };

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
@Override
    public List<Accounts> getAccountsForFMID(long fmID){
        return accountsRepo.findAll().stream()
                .filter(account -> account.getClients().getFundManager().getFmid() == fmID).collect(Collectors.toList());
    }

    @Override
    public Interest saveInterest(Interest interest) throws Exception {
        try {
            // Attempt to save the entity
            interestRepo.save(interest);
        } catch (DataIntegrityViolationException e) {
            Interest existingInterest = interestRepo.findByStartDateAndAccount(interest.getStartDate(), interest.getAccount());

            if (existingInterest != null) {
                // Update the existing record with new data
                existingInterest.setInterestRate(interest.getInterestRate());
                // Update any other fields as needed

                // Save the updated record
                interestRepo.save(existingInterest);
            } else {
                throw new Exception("Not able to insert the Interest row");
            }
        }
        return interest;
    }

    @Override
    public Investment saveInvestment(Investment investment) throws Exception {
        try {
            // Attempt to save the entity
            Investment invEntered=investmentRepo.save(investment);
            if(invEntered!=null) {
                transactionsRepo.save(Transactions.builder().fromClientId(investment.getClients()).toClientId(investment.getClients()).fromAccountId(investment.getAccounts()).toAccountId(accountsRepo.findByaccountName("Investment")).Amount(investment.getAmount()).date(investment.getTradeDate()).remarks("Invested in " + investment.getStockSymbol() + " Inv id:" + invEntered.getId()).build());
            }
            return invEntered;
        } catch (Exception e) {
            throw e;
        }
    }
}
