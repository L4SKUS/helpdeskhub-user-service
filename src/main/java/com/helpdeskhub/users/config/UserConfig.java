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

            User user1 = User.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@example.com")
                    .password("haslo1")
                    .phoneNumber("1234567890")
                    .role(UserRole.CUSTOMER)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            User user2 = User.builder()
                    .firstName("Jane")
                    .lastName("Smith")
                    .email("jane.smith@example.com")
                    .password("haslo2")
                    .phoneNumber("0987654321")
                    .role(UserRole.AGENT)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            User user3 = User.builder()
                    .firstName("Tony")
                    .lastName("Lazuto")
                    .email("tony.lazuto@example.com")
                    .password("haslo3")
                    .phoneNumber("0987654000")
                    .role(UserRole.ADMIN)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            User user4 = User.builder()
                    .firstName("Agento")
                    .lastName("Agentino")
                    .email("agento.agentino@example.com")
                    .password("haslo4")
                    .phoneNumber("12345")
                    .role(UserRole.AGENT)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            repository.saveAll(List.of(user1, user2, user3, user4));
        };
    }
}
