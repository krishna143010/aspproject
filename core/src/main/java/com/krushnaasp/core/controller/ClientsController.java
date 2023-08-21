package com.krushnaasp.core.controller;

import com.krushnaasp.core.entity.Clients;
import com.krushnaasp.core.service.ClientsSvc;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("accounts")
public class ClientsController {
//    @RequestMapping(value = "/helloWorld",method = RequestMethod.GET)
    private final Logger logger= LoggerFactory.getLogger(ClientsController.class);

    @Autowired
    private ClientsSvc clientsSvc;
    @PostMapping("/SaveClients")
    public Clients saveClients(@Valid @RequestBody Clients clients){
        //calling service
        logger.info("Logging for saveClients");
        return clientsSvc.saveClients(clients);
    }
    @GetMapping("/Clients")
    public List<Clients> fetchClients(){
        return clientsSvc.fetchClientsList();
    }
    @GetMapping("/Clients/{id}")
    public Clients fetchClientsById(@PathVariable("id") Long id)
    {
        return clientsSvc.fetchByClientsId(id);
    }
    @GetMapping("/{id}/AllClients")
    public  List<Clients>  fetchClientsByFMId(@PathVariable("id") Long id)
    {
        System.out.println("__________fetchClientsByFMId_______");
        return clientsSvc.fetchClientsListByFMid(id);
    }
    @DeleteMapping("/Clients/{id}")
    public String deleteFMById(@PathVariable("id") Long id){
        clientsSvc.deleteClientsById(id);
        return "FM deleted for id "+id;
    }
    @PutMapping("/Clients/{id}")
    public Clients updateFMById(@PathVariable("id") Long id,
                                  @RequestBody Clients clientsToBeUpdated
                                  ){
        return clientsSvc.updateClientsById(id,clientsToBeUpdated);
    }
}
