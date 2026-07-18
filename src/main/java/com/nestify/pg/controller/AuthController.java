package com.nestify.pg.controller;

import com.nestify.pg.entity.Role;
import com.nestify.pg.entity.User;
import com.nestify.pg.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            Map<String, String> result = authService.login(
                body.get("username"),
                body.get("password")
            );
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        User user = authService.register(
            body.get("username"),
            body.get("password"),
            Role.valueOf(body.getOrDefault("role", "TENANT"))
        );
        return ResponseEntity.ok(Map.of("message", "User registered", "id", user.getId()));
    }
}