package com.krushna.accountservice.config;

import com.krushna.accountservice.client.TransactionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {
    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;
    @Bean
    public WebClient transactionWebClient(){
        return WebClient
                .builder()
                .baseUrl("http://transaction-service")
                .filter(filterFunction)
                .build();
    }
    @Bean
    public TransactionClient transactionClient(){
        HttpServiceProxyFactory httpServiceProxyFactory=HttpServiceProxyFactory.builder(WebClientAdapter.forClient(transactionWebClient())).build();
        return httpServiceProxyFactory.createClient(TransactionClient.class);
    }
}
