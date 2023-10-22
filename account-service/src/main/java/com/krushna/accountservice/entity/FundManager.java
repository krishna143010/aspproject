package com.krushna.accountservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;

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
