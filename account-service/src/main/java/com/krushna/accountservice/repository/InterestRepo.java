package com.krushna.accountservice.repository;

//import com.javalearning.springbootdemo.entity.Accounts;
import com.krushna.accountservice.entity.Accounts;
import com.krushna.accountservice.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface InterestRepo extends JpaRepository<Interest, Long> {
    //fund manager by name
    public Interest findByAccount(Accounts account);
    Interest findByStartDateAndAccount(Date startDate, Accounts accounts);

}
