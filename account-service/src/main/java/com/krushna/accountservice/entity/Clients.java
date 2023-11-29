package com.krushna.accountservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(
        name = "clients", uniqueConstraints = @UniqueConstraint(columnNames = {"clientName", "fund_manager_user_info_id"})
)
public class Clients {
    @Id
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
    @OneToOne
    @MapsId
    private UserInfo userInfo;
}
