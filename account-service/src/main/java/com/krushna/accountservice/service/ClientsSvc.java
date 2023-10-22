package com.krushna.accountservice.service;

//import com.javalearning.springbootdemo.entity.Clients;
import com.krushna.accountservice.entity.Clients;

import java.util.List;

public interface ClientsSvc {


    List<Clients> fetchClientsList();

    Clients saveClients(Clients clients);
    List<Clients> fetchClientsListByFMid(Long id);

    Clients fetchByClientsId(Long id);

    public void deleteClientsById(Long id);

    Clients updateClientsById(Long id, Clients clientsToBeUpdated);
}
