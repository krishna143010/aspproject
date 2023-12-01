package com.krushna.accountservice.repository;

//import com.javalearning.springbootdemo.entity.Transactions;
import com.krushna.accountservice.entity.Accounts;
import com.krushna.accountservice.entity.FundManager;
import com.krushna.accountservice.entity.Transactions;
import com.krushna.accountservice.model.AccountSummary;
import com.krushna.accountservice.model.TxnStatementForSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepo extends JpaRepository<Transactions, Long> {
    //fund manager by name
    public Transactions findByfromClientId(long fromClientId);
    public Transactions findBytoClientId(long fromClientId);
    public Transactions findByfromAccountId(long fromAccountId);
    public Transactions findBytoAccountId(long toAccountId);
    //public AccountSummary[] getNetBalsOfAccounts(long transId);
    /*@Query(nativeQuery = true,
            value = "SELECT " +
                    "(SELECT SUM(Amount) FROM Transactions " +
                    "INNER JOIN Accounts AS t3 ON Transactions.toAccountId = t3.accountID " +
                    "INNER JOIN Clients AS t4 ON Transactions.toClientId = t4.clientID " +
                    "WHERE Accounts.accountId=?1 AND fmIdForAccount=?2) AS outMoney, " +
                    "(SELECT SUM(Amount) FROM Transactions " +
                    "INNER JOIN Accounts AS t3 ON Transactions.fromAccountId = t3.accountID " +
                    "INNER JOIN Clients AS t4 ON Transactions.fromClientId = t4.clientID " +
                    "WHERE Accounts.accountId=?3 AND fmIdForAccount=?4) AS inMoney")*/
    //Object getTransactionSummary(@Param("accountName") Long accountName, @Param("name") String name);
    @Query(nativeQuery = true,
            value = "SELECT SUM(Amount) FROM Transactions WHERE Transactions.from_account_id_account_id=?1 ")
    Long getAccountBalSummary(long accounts);
    @Query(nativeQuery = true,
            value = "SELECT SUM(Amount) FROM Transactions WHERE Transactions.from_account_id_account_id=?1 ")
    Long getTransactionSummary(long accounts);
    /*@Query(nativeQuery = true,
            value = "SELECT SUM(Amount) FROM Transactions WHERE Transactions.from_account_id_account_id=?1 ")*/
    //@Query(value = "SELECT (SELECT SUM(Amount) FROM Transactions WHERE Transactions.from_account_id_account_id=?1) - (SELECT SUM(Amount) FROM Transactions WHERE Transactions.from_account_id_account_id=?1) AS netFMBal", nativeQuery = true)
    @Query(nativeQuery = true,
            value = "SELECT "
                    +
                    "(SELECT COALESCE(SUM(Amount),0) FROM Transactions " +
                    "INNER JOIN Accounts AS t1 ON Transactions.to_account_id_account_id = t1.account_id " +
                    "INNER JOIN Clients AS t2 ON Transactions.to_client_id_client_id = t2.client_id " +
                    "WHERE fund_manager_user_info_id=?1 AND client_name='External')"

                    +"-"+
                    "(SELECT COALESCE(SUM(Amount),0) FROM Transactions " +
                    "INNER JOIN Accounts AS t3 ON Transactions.from_account_id_account_id = t3.account_id " +
                    "INNER JOIN Clients AS t4 ON Transactions.from_client_id_client_id = t4.client_id " +
                    "WHERE fund_manager_user_info_id=?1 AND client_name='External')"


                    +" AS netFMBal"
    )

    Long getTotalFundSummary(long fmID);
    @Query(nativeQuery = true,
            value = "SELECT "
                    +
                    "(SELECT COALESCE(SUM(Amount),0) FROM Transactions " +
                    "INNER JOIN Accounts AS t1 ON Transactions.to_account_id_account_id = t1.account_id " +
                    "INNER JOIN Clients AS t2 ON Transactions.to_client_id_client_id = t2.client_id " +
                    "WHERE fund_manager_user_info_id=?2 AND to_client_id_client_id=?1)"
                    +"-"+
                    "(SELECT COALESCE(SUM(Amount),0) FROM Transactions " +
                    "INNER JOIN Accounts AS t3 ON Transactions.from_account_id_account_id = t3.account_id " +
                    "INNER JOIN Clients AS t4 ON Transactions.from_client_id_client_id = t4.client_id " +
                    "WHERE fund_manager_user_info_id=?2 AND from_client_id_client_id=?1)"
                    +" AS netAccountBal"
    )

    Long getClientSummary(long clientId,long fmID);
    @Query(nativeQuery = true,
            value = "SELECT "
                    +
                    "(SELECT COALESCE(SUM(Amount),0) FROM Transactions " +
                    "INNER JOIN Accounts AS t1 ON Transactions.to_account_id_account_id = t1.account_id " +
                    "INNER JOIN Clients AS t2 ON Transactions.to_client_id_client_id = t2.client_id " +
                    "WHERE fund_manager_user_info_id=?2 AND to_account_id_account_id=?1)"
                    +"-"+
                    "(SELECT COALESCE(SUM(Amount),0) FROM Transactions " +
                    "INNER JOIN Accounts AS t3 ON Transactions.from_account_id_account_id = t3.account_id " +
                    "INNER JOIN Clients AS t4 ON Transactions.from_client_id_client_id = t4.client_id " +
                    "WHERE fund_manager_user_info_id=?2 AND from_account_id_account_id=?1)"
                    +" AS netAccountBal"
    )

    Long getAccountSummary(long accountId,long fmID);
    @Query(nativeQuery = true,
            value = "SELECT from_account_id_account_id AS accountID,from_client_id_user_info_id AS clientID,-1*amount as amount,fmid_user_info_id AS FM,date FROM transactions UNION SELECT to_account_id_account_id AS accountID,to_client_id_user_info_id AS clientID,amount,fmid_user_info_id AS FM,date FROM transactions"
    )
    List<Object> txnStatementForSummary();
}
