package com.krushnaasp.core.service;

import com.krushnaasp.core.entity.FundManager;

import java.util.List;

public interface FundManagerSvc {


    List<FundManager> fetchFundManagerList();

    FundManager saveFundManager(FundManager fundManager);

    FundManager fetchByFMId(Long id);

    public void deleteFMById(Long id);

    FundManager updateFMById(Long id, FundManager fmToBeUpdated);
}
