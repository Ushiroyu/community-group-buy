package com.example.community.controller;

import com.example.community.entity.User;
import com.example.community.service.IUserService;
import com.example.community.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final IUserService userService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(IUserService userService, JwtUtil jwtUtil) {
        this.userService = userService; this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public User register(@RequestBody User u) {
        u.setCreatedAt(LocalDateTime.now());
        return userService.register(u);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        User u = userService.findByUsername(req.getUsername());
        if (u == null || !encoder.matches(req.getPassword(), u.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        String token = jwtUtil.generateToken(u);
        return new LoginResponse(token, u);
    }

    @Data
    public static class LoginRequest { private String username, password; }
    @Data
    public static class LoginResponse { private String token; private User user; public LoginResponse(String t, User u){token=t;user=u;} }
}