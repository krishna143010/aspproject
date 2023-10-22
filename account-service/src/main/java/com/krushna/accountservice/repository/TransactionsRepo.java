package com.krushna.accountservice.repository;

//import com.javalearning.springbootdemo.entity.Transactions;
import com.krushna.accountservice.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepo extends JpaRepository<Transactions, Long> {
    //fund manager by name
    public Transactions findByfromClientId(long fromClientId);
    public Transactions findBytoClientId(long fromClientId);
    public Transactions findByfromAccountId(long fromAccountId);
    public Transactions findBytoAccountId(long toAccountId);
}
