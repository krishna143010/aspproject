package com.krushna.accountservice.repository;

//import com.javalearning.springbootdemo.entity.Accounts;
import com.krushna.accountservice.entity.Accounts;
import com.krushna.accountservice.entity.Clients;
import com.krushna.accountservice.entity.FundManager;
import com.krushna.accountservice.model.AccountStatementTxns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccountsRepo extends JpaRepository<Accounts, Long> {
    //fund manager by name
    public Accounts findByaccountName(String name);
    @Query(nativeQuery = true, value = "SELECT amount AS amount, to_account_id_account_id AS account, date, " +
            "COALESCE((SELECT interest_rate FROM interest WHERE start_date <= ?2 AND account_account_id = ?1 ORDER BY start_date DESC LIMIT 1), 0) AS interest_rate " +
            "FROM transactions WHERE to_account_id_account_id = ?1 AND date <= ?2 " +
            "UNION SELECT amount * -1 AS amount, from_account_id_account_id AS account, date, " +
            "COALESCE((SELECT interest_rate FROM interest WHERE start_date <= ?2 AND account_account_id = ?1 ORDER BY start_date DESC LIMIT 1), 0) AS interest_rate " +
            "FROM transactions WHERE from_account_id_account_id = ?1 AND date <= ?2 " +
            "UNION SELECT 0 AS amount, ?1 AS account, start_date AS date, interest_rate FROM interest WHERE start_date <= ?2 ORDER BY date ASC"
    )
    List<Object> getAccountStatement(long accountId, Date date);
    /*@Query(nativeQuery = true, value = "WITH transaction_cte AS (" +
            "  SELECT amount AS amount, to_account_id_account_id AS account, date, " +
            "         LAG(amount, 1, 0) OVER (ORDER BY date) AS accumulated_amount, " +
            "         COALESCE((SELECT interest_rate FROM interest WHERE start_date <= ?2 AND account_account_id = ?1 ORDER BY start_date DESC LIMIT 1), 0) AS interest_rate " +
            "  FROM transactions WHERE to_account_id_account_id = ?1 AND date <= ?2 " +
            "  UNION " +
            "  SELECT amount * -1 AS amount, from_account_id_account_id AS account, date, " +
            "         LAG(amount, 1, 0) OVER (ORDER BY date) AS accumulated_amount, " +
            "         COALESCE((SELECT interest_rate FROM interest WHERE start_date <= ?2 AND account_account_id = ?1 ORDER BY start_date DESC LIMIT 1), 0) AS interest_rate " +
            "  FROM transactions WHERE from_account_id_account_id = ?1 AND date <= ?2 " +
            ") " +
            "SELECT amount, account, date, accumulated_amount, interest_rate " +
            "FROM transaction_cte " +
            "UNION " +
            "SELECT 0 AS amount, ?1 AS account, start_date AS date, 0 AS accumulated_amount, interest_rate " +
            "FROM interest WHERE start_date <= ?2 ORDER BY date ASC"
    )
    List<Object> getAccountStatement(long accountId, Date date);*/

}
