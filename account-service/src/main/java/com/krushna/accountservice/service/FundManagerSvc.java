package com.krushna.accountservice.service;

//import com.javalearning.springbootdemo.entity.FundManager;
//import com.javalearning.springbootdemo.error.FundManagerNotFoundException;
import com.krushna.accountservice.entity.FundManager;

import java.util.List;

public interface FundManagerSvc {


    List<FundManager> fetchFundManagerList();

    FundManager saveFundManager(FundManager fundManager);

    FundManager fetchByFMId(Long id);

    public void deleteFMById(Long id);

    FundManager updateFMById(Long id, FundManager fmToBeUpdated);
}
