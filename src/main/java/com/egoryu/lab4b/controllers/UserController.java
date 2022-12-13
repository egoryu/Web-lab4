package com.egoryu.lab4b.controllers;

import com.egoryu.lab4b.entities.Result;
import com.egoryu.lab4b.entities.User;
import com.egoryu.lab4b.repositories.UserRepository;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/check")
    public Result checkUser(@RequestBody User user) {
        if (user == null) {
            return null;
        }

        Optional<User> client = userRepository.findById(user.getUsername());
        if (client.isEmpty()) {
            return null;
        }

        String token;
        if (createHash(user.getPassword() + client.get().getSalt()).equals(client.get().getPassword())) {
            token = randomString(32);
            userRepository.deleteById(client.get().getUsername());
            client.get().setToken(token);
            userRepository.save(client.get());

            return new Result(token, "");
        }
        return null;
    }

    @PostMapping("/users")
    public Result registration(@RequestBody User user) {
        Optional<User> client = userRepository.findById(user.getUsername());
        if (client.isPresent()) {
            return null;
        }
        user.setSalt(randomString(8));
        user.setPassword(createHash(user.getPassword() + user.getSalt()));
        user.setToken(randomString(32));
        userRepository.save(user);

        return new Result(user.getToken(), "");
    }

    @PostMapping("/active")
    public Result activeUser(@RequestBody Result data) {
        if (data == null) {
            return null;
        }
        String username = data.getResult(), token = data.getGive();
        Optional<User> client = userRepository.findById(username);
        if (client.isEmpty())
            return null;

        if (client.get().getToken().equals(token))
            return new Result("reer", "yes");
        return null;
    }

    private String createHash(String password) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hash));
    }

    public String randomString(int targetStringLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        SecureRandom random = new SecureRandom();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

    }
}
