package com.krushnaasp.core.repository;

import com.krushnaasp.core.entity.Clients;
import com.krushnaasp.core.entity.FundManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientsRepo extends JpaRepository<Clients, Long> {
    //fund manager by name
    public Clients findByclientName(String name);
    public List<Clients> findAllByfundManager(FundManager fmid);
}
