package com.krushna.accountservice.repository;

//import com.javalearning.springbootdemo.entity.Clients;
import com.krushna.accountservice.entity.Clients;
import com.krushna.accountservice.entity.FundManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientsRepo extends JpaRepository<Clients, Long> {
    //fund manager by name
    public Clients findByclientName(String name);
    public List<Clients> findAllByfundManager(FundManager fmid);
}
