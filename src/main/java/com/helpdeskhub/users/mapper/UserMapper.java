package com.helpdeskhub.users.mapper;

import com.helpdeskhub.users.dto.UserCreateDTO;
import com.helpdeskhub.users.dto.UserResponseDTO;
import com.helpdeskhub.users.dto.UserUpdateDTO;
import com.helpdeskhub.users.dto.ValidationResponseDTO;
import com.helpdeskhub.users.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserCreateDTO dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .passwordHash(dto.getPasswordHash())
                .phoneNumber(dto.getPhoneNumber())
                .role(dto.getRole())
                .build();
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }

    public ValidationResponseDTO toValidationResponseDTO(User user) {
        return ValidationResponseDTO.builder()
                .id(user.getId())
                .role(user.getRole())
                .build();
    }

    public void updateUserFromDTO(User user, UserUpdateDTO dto) {
        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPhoneNumber() != null) {
            user.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getPasswordHash() != null) {
            user.setPasswordHash(dto.getPasswordHash());
        }
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }
    }
}
