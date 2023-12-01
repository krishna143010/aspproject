package com.krushna.accountservice.service;

//import com.javalearning.springbootdemo.entity.Accounts;
//import com.javalearning.springbootdemo.repository.AccountsRepo;
import com.krushna.accountservice.entity.*;
import com.krushna.accountservice.model.AccountStatementTxns;
import com.krushna.accountservice.model.TxnStatementForSummary;
import com.krushna.accountservice.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
@Slf4j
@Service
public class AccountsSvcImpl implements AccountsSvc{
    @Autowired
    private ClientsRepo clientsRepo;
    @Autowired
    private FundManagerRepo fundManagerRepo;
    @Autowired
    private AccountsRepo accountsRepo;
    @Autowired
    private InterestRepo interestRepo;
    @Autowired
    private InvestmentRepo investmentRepo;
    @Autowired
    private TransactionsRepo transactionsRepo;
    @Autowired
    TransactionsSvc transactionsSvc;

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
    public List<AccountStatementTxns> accountStatement(long accountId, Date dateTillItGenerates, Long clinetID) throws Exception{
        List<AccountStatementTxns> accountStatementTxnList=new ArrayList<>();
        /*Long accumulatedAmountForInterestCalc= Long.valueOf(0);
        Date dateForNoOfDays = null;*/
        AccountStatementTxns prevRecord=null;
        AccountStatementTxns accountStatementTxns=null;
        boolean isFirstIteration = true;
        for (Object result : accountsRepo.getAccountStatement(accountId,dateTillItGenerates,clinetID)) {
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
        if(prevRecord!=null){
            long diffInMillies = Math.abs(dateTillItGenerates.getTime() - prevRecord.getDate().getTime());
            long days= TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            double interest= ((prevRecord.getInterestRate()/(100*365))*days* prevRecord.getAccumulatedAmountForInterestCalc())+ prevRecord.getAccumulatedInterest();
            interest = Math.round(interest * 100.0) / 100.0;
            log.info("No of days: "+days+" Interest Earned (total): "+interest+" Interest Rate: "+prevRecord.getInterestRate()+" Amount: "+0+" accumForNextTime: "+(prevRecord.getAccumulatedAmountForInterestCalc()+0));
            accountStatementTxns=new AccountStatementTxns(0L, accountId, dateTillItGenerates, prevRecord.getInterestRate(),prevRecord.getAccumulatedAmountForInterestCalc()+0, interest);
            accountStatementTxnList.add(accountStatementTxns);
        }
        return accountStatementTxnList;
    }
    private Double accountTotalInterestTillDate(long accountId, Date dateTillItGenerates,Long clinet)throws Exception{
        List<AccountStatementTxns> accountStatementTxns=accountStatement(accountId,dateTillItGenerates,clinet);
        return  accountStatementTxns.get(accountStatementTxns.size()-1).getAccumulatedInterest();
    }
    @Override
    public String accountSettlement(long accountId, Date dateTillItGenerates, String fmName) throws Exception {
        long fmid=fundManagerRepo.findByfmName(fmName).getFmid();
        Double totalInterestAccount=accountTotalInterestTillDate(accountId, dateTillItGenerates,null);
        log.info("totalInterest AccountLevel:"+totalInterestAccount);
        long settledTillNowAccount=transactionsRepo.findAll().stream().filter(item -> item.getFmid().getFmid()==fmid).filter(item->item.getFromAccountId().getAccountName().equals("Interest")).filter(item->item.getToAccountId().getAccountId()==accountId).mapToLong(Transactions::getAmount).sum();
        //long settledTillNowAccount=transactionsSvc.txnStatementSummaryList().stream().filter(item -> item.getFM()==fmid).filter(item -> item.getAccountID()==accountId).filter(item -> !item.getDate().toInstant().isAfter(dateTillItGenerates.toInstant())).mapToLong(TxnStatementForSummary::getAmount).sum();
        log.info("settledTillNow AccountLevel:"+settledTillNowAccount);
        //return (long) (totalInterest-settledTillNow);
        if(transactionsRepo.findAll().stream().filter(item -> item.getFmid().getFmid()==fmid).filter(item -> item.getFromAccountId().getAccountName().equals("Interest")).filter(item -> item.getToAccountId().getAccountId()==accountId).filter(item -> !item.getDate().toInstant().isBefore(dateTillItGenerates.toInstant())).count()>0){
            throw new Exception("We have already settled your Interest Already on or after this date");
        }
        List<Clients> avlClients=avlClientsForTheGivenAccount(accountId,fmid);
        for (Clients avlClient : avlClients) {
            Double totalInterest=accountTotalInterestTillDate(accountId, dateTillItGenerates,avlClient.getClientId());
            long settledTillNow=transactionsRepo.findAll().stream().filter(item -> item.getFmid().getFmid()==fmid).filter(item->item.getFromAccountId().getAccountName().equals("Interest")).filter(item->item.getToAccountId().getAccountId()==accountId).filter(item->item.getFromClientId().getClientId()==avlClient.getClientId()).filter(item->item.getToClientId().getClientId()==avlClient.getClientId()).mapToLong(Transactions::getAmount).sum();
            log.info("totalInterest for "+avlClient.getClientName()+": "+totalInterest);
            log.info("settledTillNow for "+avlClient.getClientName()+": "+settledTillNow);
            makeInterestTransaction(accountId, dateTillItGenerates, fmid,avlClient.getClientId(),(long) (totalInterest-settledTillNow));
        }
        return "Settled Successfully";
    }
    void makeInterestTransaction(long accountId, Date dateTillItGenerates, long fmid,long clientId, long settleAmount){
        transactionsRepo.save(Transactions.builder().fromAccountId(accountsRepo.findByaccountName("Interest")).toAccountId(accountsRepo.findById(accountId).get()).fromClientId(clientsRepo.findById(clientId).get()).toClientId(clientsRepo.findById(clientId).get()).Amount(settleAmount).date(dateTillItGenerates).remarks("Interest till:"+new SimpleDateFormat("MM-dd-yyyy").format(dateTillItGenerates)+" Recorded date in FM:"+new SimpleDateFormat("MM-dd-yyyy").format(new Date())).fmid(fundManagerRepo.findById(fmid).get()).build());
    }
    List<Clients> avlClientsForTheGivenAccount(long accountId,long fmid){
        return transactionsSvc.txnStatementSummaryList().stream().filter(item -> item.getFM()==fmid).filter(item -> item.getAccountID()==accountId).map(TxnStatementForSummary::getClientID).distinct()  // To get unique clientIDs
                .map(clientID -> clientsRepo.findById(clientID).get()) // Assuming Clients has a constructor that accepts clientID
                .collect(Collectors.toList());
    }
    List<Accounts> avlAccountsForTheGivenClient(long clientId,long fmid){
        return transactionsSvc.txnStatementSummaryList().stream().filter(item -> item.getFM()==fmid).filter(item -> item.getClientID()==clientId).map(TxnStatementForSummary::getAccountID).distinct()  // To get unique clientIDs
                .map(accountId -> accountsRepo.findById(accountId).get()) // Assuming Clients has a constructor that accepts clientID
                .collect(Collectors.toList());
    }

    ;

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
