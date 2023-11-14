package com.krushna.accountservice.service;

//import com.javalearning.springbootdemo.entity.FundManager;
//import com.javalearning.springbootdemo.error.FundManagerNotFoundException;
//import com.javalearning.springbootdemo.repository.FundManagerRepo;
import com.krushna.accountservice.entity.FundManager;
import com.krushna.accountservice.entity.UserInfo;
import com.krushna.accountservice.repository.FundManagerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FundManagerSvcImpl implements FundManagerSvc{
    @Autowired
    private FundManagerRepo fundManagerRepo;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserLoginRegisterSvc userLoginRegisterSvc;


    @Override
    public List<FundManager> fetchFundManagerList() {
        return fundManagerRepo.findAll();
    }

    @Override
    public FundManager saveFundManager(FundManager fundManager) {
        return fundManagerRepo.save(fundManager);
    }

    @Override
    public FundManager fetchByFMId(Long id)/* throws FundManagerNotFoundException*/ {
        Optional<FundManager> FMResponse= fundManagerRepo.findById(id);
        return FMResponse.get();
    }
    @Override
    public void deleteFMById(Long id) {
        fundManagerRepo.deleteById(id);
    }

    @Override
    public FundManager updateFMById(Long id, FundManager fmToBeUpdated) {
        FundManager existingData= fundManagerRepo.findById(id).get();
        if(Objects.nonNull(fmToBeUpdated.getFmName())&& !"".equalsIgnoreCase(fmToBeUpdated.getFmName())){
            existingData.setFmName(fmToBeUpdated.getFmName());
        }
        if(Objects.nonNull(fmToBeUpdated.isActiveStatus())){
            existingData.setActiveStatus(fmToBeUpdated.isActiveStatus());
        }
        if(Objects.nonNull(fmToBeUpdated.isDeleteStatus())){
            existingData.setDeleteStatus(fmToBeUpdated.isDeleteStatus());
        }
        return fundManagerRepo.save(existingData);
    }
//    @Override
//    public FundManager getFMByToken(String token){
//        UserInfo userInfo=this.userLoginRegisterSvc.getUserByUsername(this.jwtService.extractUsername(token));
//        return this.fundManagerRepo.findByuserInfo(userInfo);
//    }
}
