package com.krushna.accountservice.service;

//import com.javalearning.springbootdemo.entity.FundManager;
//import com.javalearning.springbootdemo.error.FundManagerNotFoundException;
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
    UserInfo getAUserByUsername(String username);
    UserInfo generateNewCode(String token);
    UserInfo generateNewCodeDirect(String username);
    String changePassword(String token, String newPassword) throws Exception;

    String resetPassword(String email) throws Exception;

    // UserInfo getUserByUsername(String extractUsername);
}
