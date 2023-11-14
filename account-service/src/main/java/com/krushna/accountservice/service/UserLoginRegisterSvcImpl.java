package com.krushna.accountservice.service;

//import com.javalearning.springbootdemo.entity.FundManager;
//import com.javalearning.springbootdemo.error.FundManagerNotFoundException;
//import com.javalearning.springbootdemo.repository.FundManagerRepo;
import com.krushna.accountservice.entity.FundManager;
import com.krushna.accountservice.entity.Roles;
import com.krushna.accountservice.entity.UserInfo;
import com.krushna.accountservice.entity.UserInfoListener;
import com.krushna.accountservice.error.InvalidCodeException;
import com.krushna.accountservice.error.InvalidUserException;
import com.krushna.accountservice.error.RoleNotPresentException;
import com.krushna.accountservice.error.UserNameOrEmailConflictException;
import com.krushna.accountservice.model.JmsMessageToBeSend;
import com.krushna.accountservice.model.UserActiveRequest;
import com.krushna.accountservice.model.VerifyCodeRequest;
import com.krushna.accountservice.repository.FundManagerRepo;
import com.krushna.accountservice.repository.RolesRepository;
import com.krushna.accountservice.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
@Service
@Slf4j
public class UserLoginRegisterSvcImpl implements UserLoginRegisterSvc{

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FundManagerSvc fundManagerSvc;
    @Autowired
    private JmsServiceImpl jmsService;
    @Autowired
    private UserInfoListener userInfoListener;
    public String addUser(UserInfo userInfo) throws Exception {
        Optional<Roles> availableRoles=rolesRepository.findByRole(userInfo.getRoles());
        log.info("Requested role "+userInfo.getRoles()+" Available roles "+availableRoles);
        if(availableRoles.isPresent()) {
            userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
            try{
                UserInfo userInfoAdded=userInfoRepository.save(userInfo);
                if(userInfoAdded!=null){
                    try{
                        fundManagerSvc.saveFundManager(FundManager.builder().fmName(userInfoAdded.getUsername()).activeStatus(userInfoAdded.isEnabled()).deleteStatus(false).userInfo(userInfoAdded).build());

                    }catch (Exception e){
                        throw new Exception("Save failed. Kindly check logs.");
                    }
                    jmsService.sendMessage(JmsMessageToBeSend.builder().message("Verification code is "+userInfoAdded.getAuthenticatioCode()+" and is valid until "+userInfoAdded.getAuthenticatioCodeExpiry()).subject("Code from Fund Manager Application").toemail(userInfoAdded.getEmail()).build());
                    return "User "+userInfo.getUsername()+" Added Successfully";
                }else{
                    throw new Exception("Save failed. Kindly check logs.");
                }
            }catch (DataIntegrityViolationException  e){
                throw new UserNameOrEmailConflictException("Username or Email is already registered");
            }catch (Exception e){
                throw new Exception("Save failed. Kindly check logs.");
            }
        }else{
            throw  new RoleNotPresentException("Role is not valid");
        }
    }

    @Override
    public String verifyCode(VerifyCodeRequest verifyCodeInfo) {
        Optional<UserInfo> userInfo=userInfoRepository.findByUsername(verifyCodeInfo.getUsername());
        if(userInfo.isPresent()){
            if(userInfo.get().getAuthenticatioCode().equals(verifyCodeInfo.getCodeSupplied()) && Calendar.getInstance().getTime().compareTo(userInfo.get().getAuthenticatioCodeExpiry())<0){
                userInfo.get().setAuthenticationStatus(true);
                userInfoRepository.save(userInfo.get());
                return  "Code Verified Successfully";
            }else{
                throw new InvalidCodeException(verifyCodeInfo.getCodeSupplied()+" is not valid");
            }
        }else{
            throw new InvalidUserException(verifyCodeInfo.getUsername()+" user is invalid");
        }
    }

    @Override
    public String changeUserActiveStatus(UserActiveRequest userActiveRequest) throws Exception {
        Optional<UserInfo> userInfo=userInfoRepository.findByUsername(userActiveRequest.getUsername());
        if(userInfo.isPresent()){
                userInfo.get().setEnabled(userActiveRequest.isEnableStatus());
                userInfoRepository.save(userInfo.get());
                return  ""+userActiveRequest.getUsername()+" status changes to "+userActiveRequest.isEnableStatus()+" Successfully";
        }else{
            throw new Exception("Status not changed. Please Check Logs");
        }
    }

    @Override
    public List<UserInfo> getAllUsers() {
        return userInfoRepository.findAll();
    }

    @Override
    public UserInfo getAUserById(Integer id) {
        Optional<UserInfo> UserResponse= userInfoRepository.findById(id);
        return UserResponse.get();
    }
    @Override
    public UserInfo generateNewCode(String token) {

        String username=jwtService.extractUsername(token.replace("Bearer ",""));
        UserInfo existingData= userInfoRepository.findByUsername(username).get();
        existingData.setAuthenticatioCode(userInfoListener.generateRandomString());
        existingData.setAuthenticatioCodeExpiry(userInfoListener.generateDateTime());
        return userInfoRepository.save(existingData);
    }
    @Override
    public UserInfo generateNewCodeDirect(String username) {
        UserInfo existingData= userInfoRepository.findByUsername(username).get();
        existingData.setAuthenticatioCode(userInfoListener.generateRandomString());
        existingData.setAuthenticatioCodeExpiry(userInfoListener.generateDateTime());
        return userInfoRepository.save(existingData);
    }

//    @Override
//    public UserInfo getUserByUsername(String username) {
//        return userInfoRepository.findByUsername(username).get();
//    }
}
