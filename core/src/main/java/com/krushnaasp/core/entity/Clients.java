package com.krushnaasp.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clients {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long clientId;
    @ManyToOne
    @NotNull
    private FundManager fundManager;
    /*@OneToMany(mappedBy = "clients")
    private List<Accounts> accounts;
    @OneToMany(mappedBy = "fromClientId")
    private List<Transactions> fromTrans;
    @OneToMany(mappedBy = "toClientId")
    private List<Transactions> toTrans;*/
    private String clientName;
    private String activeStatus;
    @Column(name = "timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Timestamp timestamp;
}
