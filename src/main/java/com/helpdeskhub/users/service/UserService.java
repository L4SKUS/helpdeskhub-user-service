package com.helpdeskhub.users.service;

import com.helpdeskhub.users.dto.UserCreateDTO;
import com.helpdeskhub.users.dto.UserResponseDTO;
import com.helpdeskhub.users.dto.UserUpdateDTO;
import com.helpdeskhub.users.dto.ValidationResponseDTO;
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

    public boolean validateCredentials(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return password.equals(user.getPassword());
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

    public List<UserResponseDTO> getAgents() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == UserRole.AGENT)
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

    public void deleteUser(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalStateException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }
}