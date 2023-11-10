package com.krushna.accountservice.service;

import com.krushna.accountservice.model.JmsMessageToBeSend;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsServiceImpl implements JmsService{
    @Override
    public void sendMessage(JmsMessageToBeSend message) {
        msgSender.convertAndSend("ProcessMailRequest", message);
        //msgSender.convertAndSend(JmsReceiver.Q_ONE, user);
    }

    @Autowired
    private JmsTemplate msgSender;
//    @Autowired
//    public JmsServiceImpl(JmsTemplate jmsTemplate){
//        this.jmsTemplate=jmsTemplate;
//    }
}
