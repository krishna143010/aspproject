package com.krushnaasp.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
    private String currencyType;
    private String activeStatus;
    @Column(name = "timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Timestamp timestamp;

}
