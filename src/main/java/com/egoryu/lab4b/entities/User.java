package com.egoryu.lab4b.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

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

    @OneToMany(mappedBy = "owner")
    @ToString.Exclude
    private List<Point> usersResults;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}