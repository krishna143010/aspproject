package com.krushna.accountservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

//import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transId;
    @ManyToOne
    private Clients fromClientId;
    @ManyToOne
    private Clients toClientId;
    @ManyToOne
    private Accounts fromAccountId;
    @ManyToOne
    private Accounts toAccountId;
    private String remarks;
    @ManyToOne
    @JoinColumn(name = "fmid_user_info_id")
    private FundManager fmid;
    private Date date;
    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private long Amount;
    @Column(name = "timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Timestamp timestamp;
}
