package com.krushna.accountservice.repository;

//import com.javalearning.springbootdemo.entity.Accounts;
import com.krushna.accountservice.entity.Accounts;
import com.krushna.accountservice.entity.Interest;
import com.krushna.accountservice.entity.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface InvestmentRepo extends JpaRepository<Investment, Long> {
    //fund manager by name
//    public Interest findByAccount(Accounts account);
//    Interest findByStartDateAndAccount(Date startDate, Accounts accounts);

}
