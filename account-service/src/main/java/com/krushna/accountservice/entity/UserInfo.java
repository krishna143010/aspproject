package com.krushna.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;


    @JsonBackReference  // Add this annotation
    private String password;

    private String roles;

    @JsonIgnore
    @JsonBackReference
    private String AuthenticatioCode;

    @JsonIgnore
    @JsonBackReference
    private Date AuthenticatioCodeExpiry;

    @Column(columnDefinition = "boolean default false")
    private boolean AuthenticationStatus;
    @Column(columnDefinition = "boolean default false")
    private boolean Enabled;
    @Column(columnDefinition = "boolean default false")
    private boolean firstTimeLogin;
    @Column(name = "timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Timestamp timestamp;
    /*public UserInfo(){
        this.isEnabled=false;
    }*/
}
