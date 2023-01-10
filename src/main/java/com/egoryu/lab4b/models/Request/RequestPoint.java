package com.egoryu.lab4b.models.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPoint {
    String username;
    double x;
    double y;
    double r;

    public RequestPoint(String username, double x, double y, double r) {
        this.username = username;
        this.x = x;
        this.y = y;
        this.r = r;
    }
}
