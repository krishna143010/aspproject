package com.krushna.accountservice.repository;

//import com.javalearning.springbootdemo.entity.FundManager;
import com.krushna.accountservice.entity.FundManager;
import com.krushna.accountservice.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundManagerRepo extends JpaRepository<FundManager, Long> {
    //fund manager by name
    public FundManager findByfmName(String name);
    public FundManager findByuserInfo(UserInfo userInfo);
}
