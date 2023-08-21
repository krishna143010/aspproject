package com.krushnaasp.core.repository;


import com.krushnaasp.core.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepo extends JpaRepository<Accounts, Long> {
    //fund manager by name
    public Accounts findByaccountName(String name);

}
