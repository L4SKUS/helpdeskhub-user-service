package com.helpdeskhub.users.config;

import com.helpdeskhub.users.model.User;
import com.helpdeskhub.users.enums.UserRole;
import com.helpdeskhub.users.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {

            boolean hasAdmin = repository.findAll().stream()
                .anyMatch(user -> user.getRole() == UserRole.ADMIN);

            if (!hasAdmin) {
                User defaultAdmin = User.builder()
                        .firstName("Admin")
                        .lastName("User")
                        .email("admin@domain.com")
                        .passwordHash("$2a$10$KbQiZtWxqMZ9k2FvO6yLUOao5N5pBhXV0Kw9yG4HBGupf3zgOaepy")
                        .phoneNumber("0000000000")
                        .role(UserRole.ADMIN)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                repository.save(defaultAdmin);
                System.out.println("Default admin user created: admin@domain.com");
            }
        };
    }
}
