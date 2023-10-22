package com.krushna.accountservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;
    @ManyToOne
    private Clients clients;
    /*@OneToMany(mappedBy = "fromAccountId")
    private List<Transactions> fromTrans;*/
    /*@OneToMany(mappedBy = "toAccountId")
    private List<Transactions> toTrans;*/
    private String accountName;
    private String accountNumber;
    private String upiId;
    private String status;
    @Column(name = "timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Timestamp timestamp;

}
