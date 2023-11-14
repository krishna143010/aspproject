package com.krushna.accountservice.service;

//import com.javalearning.springbootdemo.entity.FundManager;
//import com.javalearning.springbootdemo.error.FundManagerNotFoundException;
import com.krushna.accountservice.entity.FundManager;
import com.krushna.accountservice.entity.UserInfo;
import com.krushna.accountservice.model.UserActiveRequest;
import com.krushna.accountservice.model.VerifyCodeRequest;

import java.util.List;

public interface UserLoginRegisterSvc {


    String addUser(UserInfo userInfo) throws Exception;
    String verifyCode(VerifyCodeRequest verifyCodeInfo);
    String changeUserActiveStatus(UserActiveRequest userActiveRequest) throws Exception;
    List<UserInfo> getAllUsers();
    UserInfo getAUserById(Integer id);
    UserInfo generateNewCode(String token);
    UserInfo generateNewCodeDirect(String username);

   // UserInfo getUserByUsername(String extractUsername);
}
