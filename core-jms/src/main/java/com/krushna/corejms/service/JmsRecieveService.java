package com.krushna.corejms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krushna.corejms.model.JmsMessageToBeSend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class JmsRecieveService {

    @Value("${emailProcessEnabled}")
    private boolean emailProcessEnabled;
    @Autowired
    private EmailService emailService;
    @JmsListener(destination = "ProcessMailRequest")
    public void processMessage(Map message) throws JsonProcessingException {
        log.info("Message recieved"+message);
        if(emailProcessEnabled){
            emailService.sendEmail(message.get("toemail").toString(),message.get("subject").toString(),message.get("message").toString());
        }else{
            log.info("Message received but not sending to the user email");
        }
    }
}
