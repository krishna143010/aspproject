package com.krushnaasp.core.service;

import com.krushnaasp.core.entity.Clients;

import java.util.List;

public interface ClientsSvc {


    List<Clients> fetchClientsList();

    Clients saveClients(Clients clients);
    List<Clients> fetchClientsListByFMid(Long id);

    Clients fetchByClientsId(Long id);

    public void deleteClientsById(Long id);

    Clients updateClientsById(Long id, Clients clientsToBeUpdated);
}
