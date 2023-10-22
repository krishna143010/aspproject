package com.krushna.transactionservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundManager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long fmid;
    @Column(unique = true)
    @NotBlank(message = "Fund Manager Name Should Not be Empty")
    private String fmName;
    /*@OneToMany(mappedBy = "fundManager")
    private List<Clients> clients;*/
    private boolean activeStatus;
    private boolean deleteStatus;
    @Column(name = "timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Timestamp timestamp;
}
