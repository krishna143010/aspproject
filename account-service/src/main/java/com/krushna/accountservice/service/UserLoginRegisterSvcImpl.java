package com.krushna.accountservice.service;

//import com.javalearning.springbootdemo.entity.FundManager;
//import com.javalearning.springbootdemo.error.FundManagerNotFoundException;
//import com.javalearning.springbootdemo.repository.FundManagerRepo;
import com.krushna.accountservice.entity.*;
import com.krushna.accountservice.error.InvalidCodeException;
import com.krushna.accountservice.error.InvalidUserException;
import com.krushna.accountservice.error.RoleNotPresentException;
import com.krushna.accountservice.error.UserNameOrEmailConflictException;
import com.krushna.accountservice.model.JmsMessageToBeSend;
import com.krushna.accountservice.model.UserActiveRequest;
import com.krushna.accountservice.model.VerifyCodeRequest;
import com.krushna.accountservice.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import org.springframework.dao.DataIntegrityViolationException;

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
    private AccountsRepo accountsRepo;
    @Autowired
    private ClientsRepo clientsRepo;
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
                    if(userInfoAdded.getRoles().equals("ROLE_FM")){
                        try{
                            FundManager fmAdd=fundManagerSvc.saveFundManager(FundManager.builder().fmName(userInfoAdded.getUsername()).activeStatus(userInfoAdded.isEnabled()).deleteStatus(false).userInfo(userInfoAdded).build());
                            /*try{
                                Clients clientExternalAdded=clientsRepo.save(Clients.builder().clientName("External").fundManager(fmAdd).build());
                                accountsRepo.save(Accounts.builder().accountName("External").accountNumber("123456").clients(clientExternalAdded).build());
                            }catch (Exception e){
                                log.warn("Not creating the external as Client and Account "+e.getMessage());
                                throw e;
                            }*/
                        }catch (Exception e){
                            throw new Exception("User Created, But External Client Not created. You can go for verification");
                        }
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
    public UserInfo getAUserByUsername(String username) {
        Optional<UserInfo> UserResponse= userInfoRepository.findByUsername(username);
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

    @Override
    public String changePassword(String token, String newPassword) throws Exception {
        Optional<UserInfo> userInfo=userInfoRepository.findByUsername(jwtService.extractUsername(token.replace("Bearer ","")));
        if(userInfo.isPresent()){
            userInfo.get().setPassword(passwordEncoder.encode(newPassword));
            userInfo.get().setFirstTimeLogin(false);
            userInfoRepository.save(userInfo.get());
            log.info("Password for "+jwtService.extractUsername(token.replace("Bearer ",""))+" changed Successfully with "+newPassword);
            return  "Password for "+jwtService.extractUsername(token.replace("Bearer ",""))+" changed Successfully";
        }else{
            throw new Exception("Password change failed for"+jwtService.extractUsername(token.replace("Bearer ","")));
        }
    }
    @Override
    public String resetPassword(String email) throws Exception {
        Optional<UserInfo> userInfo=userInfoRepository.findByEmail(email);
        if(userInfo.isPresent()){
            String psw=userInfoListener.generateRandomString();
            userInfo.get().setPassword(passwordEncoder.encode(psw));
            userInfo.get().setFirstTimeLogin(true);
            userInfoRepository.save(userInfo.get());
            jmsService.sendMessage(JmsMessageToBeSend.builder().message("The Credentials for accessing the FM portal is Username:"+userInfo.get().getUsername()+" and One time login Password is:"+psw+" \nPlease Do Login from http://localhost:4200 and reset your Password.").subject("Reset done for your account at FM").toemail(email).build());
            log.info("The Credentials for accessing the FM portal for "+email+" is Username:"+userInfo.get().getUsername()+" and One time login Password is:"+psw);
            return  "Account reset for "+email+" changed Successfully. Please check your email for further actions";
        }else{
            throw new Exception("User not exists");
        }
    }

//    @Override
//    public UserInfo getUserByUsername(String username) {
//        return userInfoRepository.findByUsername(username).get();
//    }
}
