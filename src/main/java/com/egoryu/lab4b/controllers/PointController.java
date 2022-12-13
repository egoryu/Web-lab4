package com.egoryu.lab4b.controllers;

import com.egoryu.lab4b.entities.Point;
import com.egoryu.lab4b.repositories.PointRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PointController {
    private final PointRepository pointRepository;


    public PointController(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @GetMapping("/points")
    public List<Point> getPoints() {
        return (List<Point>) pointRepository.findAll();
    }

    @PostMapping("/points")
    public Point addPoint(@RequestBody Point point) {
        if (point.validate()) {
            point.setHit(point.checkHit());
            pointRepository.save(point);
            return point;
        } else {
            return null;
        }
    }

    @DeleteMapping("/points")
    public void clearPoints() {
        pointRepository.deleteAll();
    }
}
