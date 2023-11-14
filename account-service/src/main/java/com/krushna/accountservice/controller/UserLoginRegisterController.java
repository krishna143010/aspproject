package com.krushna.accountservice.controller;

import com.krushna.accountservice.entity.FundManager;
import com.krushna.accountservice.entity.UserInfo;
import com.krushna.accountservice.model.AuthRequest;
import com.krushna.accountservice.model.UserActiveRequest;
import com.krushna.accountservice.model.VerifyCodeRequest;
import com.krushna.accountservice.service.FundManagerSvc;
import com.krushna.accountservice.service.JwtService;
import com.krushna.accountservice.service.UserLoginRegisterSvc;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user-service")
public class UserLoginRegisterController {
    private final Logger logger= LoggerFactory.getLogger(UserLoginRegisterController.class);
    @Autowired
    private UserLoginRegisterSvc userLoginRegisterSvc;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/addUser")
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserInfo userInfo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //calling service
        logger.info("Logging for add user "+userInfo);
        String resp=userLoginRegisterSvc.addUser(userInfo);
        return ResponseEntity.status(HttpStatus.OK).body(resp);

    }
    @PostMapping("/verifyCode")
    public ResponseEntity<Object> verifyCode(@Valid @RequestBody VerifyCodeRequest verifyCodeRequest, HttpServletRequest request, HttpServletResponse response){

        logger.info("verifyCode request "+verifyCodeRequest);

        return ResponseEntity.status(HttpStatus.OK).body(userLoginRegisterSvc.verifyCode(verifyCodeRequest));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + "or hasAuthority('ROLE_FM')")
    @PostMapping("/generateNewCode")
    public ResponseEntity<Object> generateNewCode(HttpServletRequest request, HttpServletResponse response){

        logger.info("generateNewCode____");

        return ResponseEntity.status(HttpStatus.OK).body(userLoginRegisterSvc.generateNewCode(request.getHeader("Authorization")));
    }
    @PostMapping("/generateNewCodeDirect")
    public ResponseEntity<Object> generateNewCodeDirect(@RequestParam(value="username") String username, HttpServletRequest request, HttpServletResponse response){

        logger.info("generateNewCode directly____");
        userLoginRegisterSvc.generateNewCodeDirect(username);
        return ResponseEntity.status(HttpStatus.OK).body("Generated successfully");
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + "or hasAuthority('ROLE_FM')")
    @PostMapping("/changeUserLoginStatus")
    public ResponseEntity<Object> activateUser(@Valid @RequestBody UserActiveRequest activateUserRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.info("activateUser request "+activateUserRequest);

        return ResponseEntity.status(HttpStatus.OK).body(userLoginRegisterSvc.changeUserActiveStatus(activateUserRequest));
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        logger.info("Authenticating____");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + "or hasAuthority('ROLE_FM')")
    @GetMapping("/Users/{id}")
    public ResponseEntity<Object> getUsersById(@PathVariable("id") Integer id,HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.info("Get user by id:"+id);

        return ResponseEntity.status(HttpStatus.OK).body(userLoginRegisterSvc.getAUserById(id));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/Users")
    public ResponseEntity<Object> getAllUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("Get all request ");

        return ResponseEntity.status(HttpStatus.OK).body(userLoginRegisterSvc.getAllUsers());
    }

}
