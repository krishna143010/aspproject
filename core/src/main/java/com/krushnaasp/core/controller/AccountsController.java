package com.krushnaasp.core.controller;

import com.krushnaasp.core.entity.Accounts;
import com.krushnaasp.core.service.AccountsSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("accounts")
public class AccountsController {
//    @RequestMapping(value = "/helloWorld",method = RequestMethod.GET)
    private final Logger logger= LoggerFactory.getLogger(AccountsController.class);

    @Autowired
    private AccountsSvc accountsSvc;
    @PostMapping("/saveAccounts")
    public Accounts saveAccounts(@RequestBody Accounts accounts){
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
}
