package com.egoryu.lab4b.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "points")
public class Point implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "x", nullable = false)
    private double x;
    @Column(name = "y", nullable = false)
    private double y;
    @Column(name = "r", nullable = false)
    private double r;
    @Column(name = "hit", nullable = false)
    private boolean hit;

    public Point(Double x, Double y, Double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = checkHit();
    }

    private boolean checkTriangle() {
        return x >= 0 && y <= 0 && y >= (2 * x - r);
    }

    private boolean checkRectangle() {
        return x <= 0 && y >= 0 && x >= -r && y <= r / 2;
    }

    private boolean checkCircle() {
        return x >= 0 && y >= 0 && x * x + y * y <= r * r / 4;
    }


    public boolean checkHit() {
        return checkTriangle() || checkRectangle() || checkCircle();
    }
}