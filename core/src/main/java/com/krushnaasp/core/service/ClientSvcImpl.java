package com.krushnaasp.core.service;

import com.krushnaasp.core.entity.Clients;
import com.krushnaasp.core.entity.FundManager;
import com.krushnaasp.core.repository.ClientsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientSvcImpl implements ClientsSvc{
    @Autowired
    private ClientsRepo clientsRepo;

    @Override
    public List<Clients> fetchClientsList() {
        return clientsRepo.findAll();
    }//findAllByfundManager
    @Override
    public List<Clients> fetchClientsListByFMid(Long id) {
        return clientsRepo.findAllByfundManager(new FundManager(id,"","","","",0,false,false,null));
    }
    @Override
    public Clients saveClients(Clients Clients) {
        return clientsRepo.save(Clients);
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
