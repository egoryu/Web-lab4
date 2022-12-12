package com.egoryu.lab4b.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "token")
    private String token;

    public User(String username, String password, String salt, String token) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.token = token;
    }
}