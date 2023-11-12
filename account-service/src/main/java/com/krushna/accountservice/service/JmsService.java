package com.krushna.accountservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.krushna.accountservice.model.JmsMessageToBeSend;

public interface JmsService {
    void sendMessage(JmsMessageToBeSend message) throws JsonProcessingException;
}
