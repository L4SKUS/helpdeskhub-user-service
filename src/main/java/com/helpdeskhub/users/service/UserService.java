package com.helpdeskhub.users.service;

import com.helpdeskhub.users.dto.*;
import com.helpdeskhub.users.enums.UserRole;
import com.helpdeskhub.users.mapper.UserMapper;
import com.helpdeskhub.users.model.User;
import com.helpdeskhub.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO createUser(UserCreateDTO dto) {
        User user = userMapper.toUser(dto);
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponseDTO(savedUser);
    }

    public boolean validateCredentials(String email, String passwordHash) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return passwordHash.equals(user.getPasswordHash());
        }
        return false;
    }

    public ValidationResponseDTO getValidationResponse(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toValidationResponseDTO)
                .orElseThrow(() -> new IllegalStateException("User not found with email: " + email));
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponseDTO)
                .collect(Collectors.toList());
    }

    public List<UserResponseDTO> getEmployees() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == UserRole.EMPLOYEE || user.getRole() == UserRole.ADMIN)
                .map(userMapper::toUserResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Integer userId) {
        return userRepository.findById(userId)
                .map(userMapper::toUserResponseDTO)
                .orElseThrow(() -> new IllegalStateException("User not found with id: " + userId));
    }

    public UserResponseDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toUserResponseDTO)
                .orElseThrow(() -> new IllegalStateException("User not found with email: " + email));
    }

    public UserResponseDTO updateUser(Integer userId, UserUpdateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found with id: " + userId));
        userMapper.updateUserFromDTO(user, dto);
        User updatedUser = userRepository.save(user);
        return userMapper.toUserResponseDTO(updatedUser);
    }

    public void changePassword(Integer userId, PasswordUpdateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found with id: " + userId));
        if (!dto.getCurrentPasswordHash().equals(user.getPasswordHash())) {
            throw new IllegalArgumentException("Current password is incorrect.");
        }
        user.setPasswordHash(dto.getNewPasswordHash());
        userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalStateException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }
}