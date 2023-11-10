package com.krushna.accountservice.entity;

import jakarta.persistence.*;
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
@EntityListeners(UserInfoListener.class)
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String roles;
    private String AuthenticatioCode;
    //@Column(name = "AuthenticatioCodeExpiry", nullable = false, updatable = false, insertable = false)
    private Date AuthenticatioCodeExpiry;
    @Column(columnDefinition = "boolean default false")
    private boolean AuthenticationStatus;
    @Column(columnDefinition = "boolean default false")
    private boolean Enabled;
    @Column(name = "timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Timestamp timestamp;
    /*public UserInfo(){
        this.isEnabled=false;
    }*/
}
