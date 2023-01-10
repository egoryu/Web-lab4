package com.egoryu.lab4b.controllers;

import com.egoryu.lab4b.entities.BlackList;
import com.egoryu.lab4b.models.Request.RequestUser;
import com.egoryu.lab4b.models.Response.ResponseString;
import com.egoryu.lab4b.entities.User;
import com.egoryu.lab4b.repositories.BlackListRepository;
import com.egoryu.lab4b.repositories.UserRepository;
import com.egoryu.lab4b.srcurity.AuthTokenFilter;
import com.egoryu.lab4b.srcurity.JwtTokenUtils;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final BlackListRepository blackListRepository;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, BlackListRepository blackListRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.blackListRepository = blackListRepository;
    }

    @PostMapping("/auth/check")
    public ResponseString authenticateUser(@Valid @RequestBody RequestUser client) {
        Optional<User> user = userRepository.findById(client.getUsername());

        if (user.isEmpty()) {
            return null;
        }

        if (!passwordEncoder.matches(client.getPassword(), user.get().getPassword()))
            return null;

        String jwt = JwtTokenUtils.generateJwtToken(user.get().getUsername());

        return new ResponseString(jwt);
    }

    @PostMapping("/auth/users")
    public ResponseString registerUser(@Valid @RequestBody RequestUser user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return null;
        }

        User newUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()));

        userRepository.save(newUser);

        String jwt = JwtTokenUtils.generateJwtToken(user.getUsername());

        return new ResponseString(jwt);
    }

    @PostMapping("/logout")
    public ResponseString logoutUser(@Valid @RequestBody String token) {
        blackListRepository.save(new BlackList(token));
        return new ResponseString("successful");
    }
}
