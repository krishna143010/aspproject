package com.krushna.accountservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(
        name = "interest",uniqueConstraints = @UniqueConstraint(columnNames = {"startDate", "account_account_id"})
)
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private float interestRate;
    private Date startDate;
    @Column(name = "timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Timestamp timestamp;
    @ManyToOne
    private Accounts account;
}
