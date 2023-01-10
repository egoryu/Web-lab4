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
    private String hit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner", referencedColumnName = "username")
    private User owner;
    public Point(Double x, Double y, Double r, User owner) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = checkHit();
        this.owner = owner;
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

    private boolean checkX() {
        return x > -5 && x < 3;
    }

    private boolean checkY() {
        return y > -5 && y < 5;
    }

    private boolean checkR() {
        return r > 0 && r <= 3;
    }

    public boolean validate() {
        return checkX() && checkY() && checkR();
    }
    public String checkHit() {
        return checkTriangle() || checkRectangle() || checkCircle() ? "hit" : "miss";
    }
}