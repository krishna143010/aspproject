package com.krushna.accountservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krushna.accountservice.model.JmsMessageToBeSend;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JmsServiceImpl implements JmsService{
    @Override
    public void sendMessage(JmsMessageToBeSend message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> messageMap=new HashMap<>();
        messageMap.put("toemail",message.getToemail());
        messageMap.put("subject",message.getSubject());
        messageMap.put("message",message.getMessage());
        msgSender.convertAndSend("ProcessMailRequest", messageMap);
        //msgSender.convertAndSend(JmsReceiver.Q_ONE, user);
    }

    @Autowired
    private JmsTemplate msgSender;
}
