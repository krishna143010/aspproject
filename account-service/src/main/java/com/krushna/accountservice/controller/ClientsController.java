package com.krushna.accountservice.controller;

//import com.javalearning.springbootdemo.entity.Clients;
//import com.javalearning.springbootdemo.service.ClientsSvc;
import com.krushna.accountservice.entity.Clients;
import com.krushna.accountservice.service.ClientsSvc;
import com.krushna.accountservice.service.FundManagerSvc;
import com.krushna.accountservice.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("accounts")
public class ClientsController {
//    @RequestMapping(value = "/helloWorld",method = RequestMethod.GET)
    private final Logger logger= LoggerFactory.getLogger(ClientsController.class);

    @Autowired
    private ClientsSvc clientsSvc;
    @Autowired
    private FundManagerSvc fundManagerSvc;
    @PostMapping(value ="/saveClients", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Clients saveClients(@Valid @RequestBody Clients clients, HttpServletRequest request, HttpServletResponse response){
        //logger.info("FM details are:"+fundManagerSvc.getFMByToken(request.getHeader("Authorization")));
        //clients.setFundManager(fundManagerSvc.getFMByToken(request.getHeader("Authorization")));
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
