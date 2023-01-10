package com.egoryu.lab4b.controllers;

import com.egoryu.lab4b.entities.Point;
import com.egoryu.lab4b.entities.User;
import com.egoryu.lab4b.models.Request.RequestPoint;
import com.egoryu.lab4b.models.Response.ResponsePoint;
import com.egoryu.lab4b.models.Response.ResponseString;
import com.egoryu.lab4b.repositories.PointRepository;
import com.egoryu.lab4b.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/main")
@RequestMapping("/point")
public class PointController {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;


    public PointController(PointRepository pointRepository, UserRepository userRepository) {
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/points")
    public List<ResponsePoint> getPoints() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ResponsePoint> ans = new ArrayList<>();
        Optional<User> user = userRepository.findById(username);

        if (user.isEmpty()) {
            return null;
        }
        user.get().getUsersResults().forEach(data -> ans.add(new ResponsePoint(data.getX(), data.getY(), data.getR(), data.getHit())));
        if (ans.size() == 0) {
            return null;
        }
        return ans;
    }

    @PostMapping("/points")
    public ResponsePoint addPoint(@RequestBody RequestPoint requestPoint) {
        if (requestPoint == null)
            return null;

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findById(username);

        if (user.isEmpty()) {
            return null;
        }

        Point point = new Point(requestPoint.getX(), requestPoint.getY(), requestPoint.getR(), user.get());
        if (point.validate()) {
            pointRepository.save(point);
            return new ResponsePoint(point.getX(), point.getY(), point.getR(), point.getHit());
        } else {
            return null;
        }
    }

    @DeleteMapping("/points")
    public ResponseString clearPoints() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findById(username);

        if (user.isEmpty()) {
            return new ResponseString("bad");
        }
        pointRepository.deleteAll(user.get().getUsersResults());
        return new ResponseString("successful");
    }
}
