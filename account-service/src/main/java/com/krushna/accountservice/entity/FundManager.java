package com.krushna.accountservice.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FundManager {
    @Id
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
    @OneToOne
    @MapsId
    private UserInfo userInfo;

}
