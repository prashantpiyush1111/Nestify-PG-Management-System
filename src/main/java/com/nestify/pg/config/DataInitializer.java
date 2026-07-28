package com.nestify.pg.config;

import com.nestify.pg.entity.Role;
import com.nestify.pg.entity.User;
import com.nestify.pg.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User(
                    null,
                    "admin",
                    passwordEncoder.encode("admin123"),
                    Role.ADMIN
            );
            userRepository.save(admin);
            System.out.println("Default admin created.");
        }

        if (userRepository.findByUsername("tenant1").isEmpty()) {
            User tenant = new User(
                    null,
                    "tenant1",
                    passwordEncoder.encode("tenant123"),
                    Role.TENANT
            );
            userRepository.save(tenant);
            System.out.println("Default tenant created.");
        }
    }
}