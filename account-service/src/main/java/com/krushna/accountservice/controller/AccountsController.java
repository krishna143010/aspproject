package com.krushna.accountservice.controller;

import com.krushna.accountservice.entity.Accounts;
import com.krushna.accountservice.entity.Interest;
import com.krushna.accountservice.entity.Investment;
import com.krushna.accountservice.model.AccountStatementTxns;
import com.krushna.accountservice.model.TxnStatementForSummary;
import com.krushna.accountservice.repository.TransactionsRepo;
import com.krushna.accountservice.service.AccountsSvc;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("accounts")
public class AccountsController {
//    @RequestMapping(value = "/helloWorld",method = RequestMethod.GET)
    private final Logger logger= LoggerFactory.getLogger(AccountsController.class);

    @Autowired
    private AccountsSvc accountsSvc;
    @Autowired
    private TransactionsRepo transactionsRepo;

    @PostMapping("/saveAccounts")
    public Accounts saveAccounts(@RequestBody Accounts accounts) throws Exception {
        //calling service
        logger.info("Logging for saveAccounts");
        return accountsSvc.saveAccounts(accounts);
    }
    @GetMapping("/Accounts")
    public List<Accounts> fetchAccounts(){
        return accountsSvc.fetchAccountsList();
    }
    @GetMapping("/Accounts/{id}")
    public Accounts fetchAccountsById(@PathVariable("id") Long id) {
        return accountsSvc.fetchByAccountsId(id);
    }
    @DeleteMapping("/Accounts/{id}")
    public String deleteFMById(@PathVariable("id") Long id){
        accountsSvc.deleteAccountsById(id);
        return "FM deleted for id "+id;
    }
    @PutMapping("/Accounts/{id}")
    public Accounts updateFMById(@PathVariable("id") Long id,
                                  @RequestBody Accounts accountsToBeUpdated
                                  ){
        return accountsSvc.updateAccountsById(id,accountsToBeUpdated);
    }
    @GetMapping("/Accounts/AllUnderFM/{id}")
    public List<Accounts> getAllAccountsUnderFM(@PathVariable("id") Long id
    ){
        return accountsSvc.getAccountsForFMID(id);
    }
    @GetMapping("/gatewaytest")
    public String gatewaytest(){
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
                logger.info("Unexpected result type: " + result.getClass());
            }
        }
        logger.info("gateway working in account service"+txnStatementForSummaryList);
        return "gateway working in account service"+txnStatementForSummaryList;
    }
    @PreAuthorize("hasAuthority('ROLE_FM')")
    @PostMapping("/addInterest")
    public ResponseEntity<Object> addInterest(@RequestParam(value="accountId") String accountId, @RequestParam(value="startDate")@DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate, @RequestParam(value="interestRate") String interestRate, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("adding interest record"+request);
        accountsSvc.saveInterest(Interest.builder().account(Accounts.builder().accountId(Long.parseLong(accountId)).build()).interestRate(Float.parseFloat(interestRate)).startDate(startDate).build());
        return ResponseEntity.status(HttpStatus.OK).body("Generated successfully");
    }
    @PreAuthorize("hasAuthority('ROLE_FM')")
    @PostMapping("/settleInterest")
    public ResponseEntity<Object> settleInterest(@RequestParam(value="accountId") String accountId, @RequestParam(value="settleDate")@DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate, HttpServletRequest request, HttpServletResponse response, Principal principal) throws Exception {
        logger.info("settling interest for account: "+accountId+" Date"+startDate);
        //List<AccountStatementTxns> accountStatementTxns= accountsSvc.accountStatement(Long.parseLong(accountId),startDate);
        String settlementMessage=accountsSvc.accountSettlement(Long.parseLong(accountId),startDate,principal.getName());
        logger.info("Settle status:"+settlementMessage);
        return ResponseEntity.status(HttpStatus.OK).body(settlementMessage);
    }
    @PreAuthorize("hasAuthority('ROLE_FM')")
    @PostMapping("/saveInvestmentTxn")
    public ResponseEntity<Object> saveInvestmentTxn(@RequestBody Investment investmentRecord, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("adding investment record"+request);
        accountsSvc.saveInvestment(investmentRecord);
        return ResponseEntity.status(HttpStatus.OK).body("Generated successfully");
    }
}
