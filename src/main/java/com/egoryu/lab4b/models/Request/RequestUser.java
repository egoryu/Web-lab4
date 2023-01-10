package com.egoryu.lab4b.models.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUser {
    String username;
    String password;

    public RequestUser(String username, String token) {
        this.username = username;
        this.password = token;
    }
}
