package com.krushna.accountservice.repository;

//import com.javalearning.springbootdemo.entity.Accounts;
import com.krushna.accountservice.entity.Accounts;
import com.krushna.accountservice.entity.Clients;
import com.krushna.accountservice.entity.FundManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepo extends JpaRepository<Accounts, Long> {
    //fund manager by name
    public Accounts findByaccountName(String name);

}
