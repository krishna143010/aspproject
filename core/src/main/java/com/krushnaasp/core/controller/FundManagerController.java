package com.krushnaasp.core.controller;

import com.krushnaasp.core.entity.FundManager;
import com.krushnaasp.core.entity.Transactions;
import com.krushnaasp.core.service.FundManagerSvc;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("FundManager")
public class FundManagerController {
//    @RequestMapping(value = "/helloWorld",method = RequestMethod.GET)
    private final Logger logger= LoggerFactory.getLogger(FundManagerController.class);

    @Autowired
    private FundManagerSvc fundManagerSvc;
    @PostMapping("/SaveFundManager")
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
    public FundManager fetchFundManagerById(@PathVariable("id") Long id) //*throws FundManagerNotFoundException
     {
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

    /*@Autowired
    private TransactionClient transactionClient;
    @GetMapping("/TransactionsFromTS/{id}")
    public Transactions fetchTransactionsForId(@PathVariable Long id){

        return transactionClient.fetchTransactionsById(id);
    }*/
}
