package com.krushna.accountservice.service;

import com.krushna.accountservice.model.JmsMessageToBeSend;

public interface JmsService {
    void sendMessage(JmsMessageToBeSend message);
}
