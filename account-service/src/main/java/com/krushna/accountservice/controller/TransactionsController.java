package com.krushna.accountservice.controller;

//import com.javalearning.springbootdemo.entity.Transactions;
//import com.javalearning.springbootdemo.service.TransactionsSvc;
import com.krushna.accountservice.entity.Transactions;
import com.krushna.accountservice.service.TransactionsSvc;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionsController {
//    @RequestMapping(value = "/helloWorld",method = RequestMethod.GET)
    private final Logger logger= LoggerFactory.getLogger(TransactionsController.class);

    @Autowired
    private TransactionsSvc transactionsSvc;
    @PostMapping("/saveTransactions")
    public Transactions saveTransactions(@Valid @RequestBody Transactions transactions){
        //calling service
        logger.info("Logging for saveTransactions");
        return transactionsSvc.saveTransactions(transactions);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/Transactions")
    public List<Transactions> fetchTransactions(){
        return transactionsSvc.fetchTransactionsList();
    }
    @GetMapping("/Transactions/{id}")
    public Transactions fetchTransactionsById(@PathVariable("id") Long id) {
        return transactionsSvc.fetchByTransactionsId(id);
    }
    @DeleteMapping("/Transactions/{id}")
    public String deleteFMById(@PathVariable("id") Long id){
        transactionsSvc.deleteTransactionsById(id);
        return "FM deleted for id "+id;
    }
    @PutMapping("/Transactions/{id}")
    public Transactions updateFMById(@PathVariable("id") Long id,
                                  @RequestBody Transactions transactionsToBeUpdated
                                  ){
        return transactionsSvc.updateTransactionsById(id,transactionsToBeUpdated);
    }
}
