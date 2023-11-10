package com.krushna.accountservice.controller;

//import com.javalearning.springbootdemo.entity.FundManager;
//import com.javalearning.springbootdemo.error.FundManagerNotFoundException;
//import com.javalearning.springbootdemo.service.FundManagerSvc;
import com.krushna.accountservice.client.TransactionClient;
import com.krushna.accountservice.entity.FundManager;
import com.krushna.accountservice.entity.Transactions;
import com.krushna.accountservice.entity.UserInfo;
import com.krushna.accountservice.service.FundManagerSvc;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("accounts")
public class FundManagerController {
//    @RequestMapping(value = "/helloWorld",method = RequestMethod.GET)
    private final Logger logger= LoggerFactory.getLogger(FundManagerController.class);

    @Autowired
    private FundManagerSvc fundManagerSvc;
    @PostMapping("/saveFundManager")
    public FundManager saveFundManager(@Valid @RequestBody FundManager fundManager){
        //calling service
        logger.info("Logging for saveFundManager");
        return fundManagerSvc.saveFundManager(fundManager);
    }
    @GetMapping("/FundManagers")
    public List<FundManager> fetchFundManagers(){
        return fundManagerSvc.fetchFundManagerList();
    }
    @GetMapping("/FundManagers/{id}")
    public FundManager fetchFundManagerById(@PathVariable("id") Long id) /*throws FundManagerNotFoundException */{
        return fundManagerSvc.fetchByFMId(id);
    }
    @DeleteMapping("/FundManagers/{id}")
    public String deleteFMById(@PathVariable("id") Long id){
        fundManagerSvc.deleteFMById(id);
        return "FM deleted for id "+id;
    }
    @PutMapping("/FundManagers/{id}")
    public FundManager updateFMById(@PathVariable("id") Long id,
                                  @RequestBody FundManager fmToBeUpdated
                                  ){
        return fundManagerSvc.updateFMById(id,fmToBeUpdated);
    }

    @Autowired
    private TransactionClient transactionClient;
    @GetMapping("/TransactionsFromTS/{id}")
    public Transactions fetchTransactionsForId(@PathVariable Long id){

        return transactionClient.fetchTransactionsById(id);
    }

}
