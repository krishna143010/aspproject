package com.krushnaasp.core.repository;


import com.krushnaasp.core.entity.Accounts;
import com.krushnaasp.core.entity.Clients;
import com.krushnaasp.core.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepo extends JpaRepository<Transactions, Long> {
    //fund manager by name
    public Transactions findByfromClientId(Clients fromClientId);
    public Transactions findBytoClientId(Clients fromClientId);
    public Transactions findByfromAccountId(Accounts fromAccountId);
    public Transactions findBytoAccountId(Accounts toAccountId);
}
