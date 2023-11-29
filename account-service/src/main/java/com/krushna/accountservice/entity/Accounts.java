package com.krushna.accountservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

//import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
/*@Table(
        name = "accounts",uniqueConstraints = @UniqueConstraint(columnNames = {"accountName", "clients_client_id "})
)*/
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;
    @ManyToOne
    @NotNull
    private Clients clients;
    @Column(unique = true)
    private String accountName;
    private String accountNumber;
    private String upiId;
    private String status;
    @Column(name = "timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Timestamp timestamp;

}
