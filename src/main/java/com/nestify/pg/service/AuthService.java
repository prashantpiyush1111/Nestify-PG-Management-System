package com.nestify.pg.service;

import com.nestify.pg.entity.Role;
import com.nestify.pg.entity.User;
import com.nestify.pg.repository.UserRepository;
import com.nestify.pg.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, String> login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(username, user.getRole().name());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole().name());
        response.put("username", username);
        return response;
    }

    public User register(String username, String password, Role role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        // Public registration can only ever create TENANT accounts.
        // ADMIN accounts must be created manually (DB seed/migration) or via
        // a separate admin-only endpoint protected by an existing admin's JWT.
        if (role == Role.ADMIN) {
            throw new RuntimeException("Cannot self-register as ADMIN");
        }
        User user = new User(null, username, passwordEncoder.encode(password), Role.TENANT);
        return userRepository.save(user);
    }
}