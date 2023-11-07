package com.krushna.accountservice.entity;

import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    private String roles;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ActivationCodeExpiry", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp ActivationCodeExpiry;
    @Column(columnDefinition = "boolean default false")
    private boolean Enabled;
    @Column(name = "timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Timestamp timestamp;
    /*public UserInfo(){
        this.isEnabled=false;
    }*/
}
