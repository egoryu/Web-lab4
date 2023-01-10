package com.egoryu.lab4b.models.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePoint {
    double x;
    double y;
    double r;
    String h;

    public ResponsePoint(double x, double y, double r, String h) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.h = h;
    }
}
