package com.krushna.accountservice.service;

//import com.javalearning.springbootdemo.entity.Clients;
//import com.javalearning.springbootdemo.repository.ClientsRepo;
import com.krushna.accountservice.entity.*;
import com.krushna.accountservice.error.UserNameOrEmailConflictException;
import com.krushna.accountservice.model.JmsMessageToBeSend;
import com.krushna.accountservice.repository.AccountsRepo;
import com.krushna.accountservice.repository.ClientsRepo;
import com.krushna.accountservice.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ClientSvcImpl implements ClientsSvc{
    @Autowired
    private ClientsRepo clientsRepo;
    @Autowired
    private AccountsRepo accountsRepo;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserInfoListener userInfoListener;
    @Autowired
    private JmsService jmsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Clients> fetchClientsList() {
        return clientsRepo.findAll();
    }//findAllByfundManager
    @Override
    public List<Clients> fetchClientsListByFMid(Long id) {
        //return clientsRepo.findAllByfundManager(new FundManager(id,"tdfdg",false,false,null));
        return clientsRepo.findAllByfundManager(FundManager.builder().fmid(id).build());
    }
    @Override
    public Clients saveClients(Clients clients, String email) throws Exception {
        String psw=userInfoListener.generateRandomString();
        log.info("Password for the client is:"+psw);
        if(clientsRepo.findByclientName(clients.getClientName())==null){
            try{
                if(clients.getClientName().equalsIgnoreCase("External")){
                    throw new Exception("Using Client Name as External is not allowed");
                }
                UserInfo userInfoAdded=userInfoRepository.save(UserInfo.builder().Enabled(true).email(email).roles("ROLE_CLIENT").AuthenticationStatus(true).username(email.split("@")[0]).password(passwordEncoder.encode(psw)).firstTimeLogin(true).build());
                if(userInfoAdded!=null){
                    clients.setUserInfo(userInfoAdded);
                    clients.setActiveStatus("true");
                    log.info("User added successfully for Client and going for client");
                    Clients clientSaved=clientsRepo.save(clients);
                    jmsService.sendMessage(JmsMessageToBeSend.builder().message("The FundManager "+clientsRepo.findByclientName(clientSaved.getClientName()).getFundManager().getFmName()+" registered you as a Client and The Credentials for accessing the details is Username:"+userInfoAdded.getUsername()+" and One time login Password is:"+psw+" \nPlease Do Login from http://localhost:4200 and reset your Password.").subject("Client Account Created in FM app and Password Reset Required").toemail(userInfoAdded.getEmail()).build());
                    if(accountsRepo.findByaccountName("External")==null) {
                        accountsRepo.save(Accounts.builder().clients(clientSaved).accountName("External").accountNumber("123").upiId("dummy@upi").status("ACTIVE").build());
                    }
                    if(accountsRepo.findByaccountName("Investment")==null) {
                        accountsRepo.save(Accounts.builder().clients(clientSaved).accountName("Investment").accountNumber("123").upiId("dummy@upi").status("ACTIVE").build());
                    }
                    if(accountsRepo.findByaccountName("Interest")==null) {
                        accountsRepo.save(Accounts.builder().clients(clientSaved).accountName("Interest").accountNumber("123").upiId("dummy@upi").status("ACTIVE").build());
                    }
                    if(clientsRepo.findByclientName("External")==null) {
                        UserInfo userInfoForExternalClient=userInfoRepository.save(UserInfo.builder().Enabled(false).email("external@gmail.com").roles("ROLE_CLIENT").AuthenticationStatus(false).username("external").password(passwordEncoder.encode(psw)).firstTimeLogin(true).build());
                        clientsRepo.save(Clients.builder().clientName("External").fundManager(clientSaved.getFundManager()).userInfo(userInfoForExternalClient).build());
                    }
                    return clientSaved;
                }else{
                    throw new Exception("Save failed at UserInfo Creation. Kindly check logs.");
                }
            }catch (DataIntegrityViolationException e){
                throw new UserNameOrEmailConflictException("Username or Email is already registered");
            }catch (Exception e){
                throw e;
            }
        }else{
            throw new Exception("Client Name:"+clients.getClientName()+ " Already Used.\nTry with different Name.");
        }


    }

    @Override
    public Clients fetchByClientsId(Long id){
        Optional<Clients> FMResponse= clientsRepo.findById(id);
        return FMResponse.get();
    }
    @Override
    public void deleteClientsById(Long id) {
        clientsRepo.deleteById(id);
    }

    @Override
    public Clients updateClientsById(Long id, Clients clientToBeUpdated) {
        Clients existingData= clientsRepo.findById(id).get();
        if(Objects.nonNull(clientToBeUpdated.getClientName())&& !"".equalsIgnoreCase(clientToBeUpdated.getClientName())){
            existingData.setClientName(clientToBeUpdated.getClientName());
        }
        if(Objects.nonNull(clientToBeUpdated.getActiveStatus())&& !"".equalsIgnoreCase(clientToBeUpdated.getActiveStatus())){
            existingData.setActiveStatus(clientToBeUpdated.getActiveStatus());
        }
        if(Objects.nonNull(clientToBeUpdated.getFundManager())){
            existingData.setFundManager(clientToBeUpdated.getFundManager());
        }

        return clientsRepo.save(existingData);
    }
}
