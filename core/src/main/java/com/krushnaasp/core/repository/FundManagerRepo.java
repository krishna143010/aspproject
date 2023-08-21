package com.krushnaasp.core.repository;

import com.krushnaasp.core.entity.FundManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundManagerRepo extends JpaRepository<FundManager, Long> {
    //fund manager by name
    public FundManager findByfmName(String name);
}
