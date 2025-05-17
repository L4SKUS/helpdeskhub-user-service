package com.helpdeskhub.users.dto;

import com.helpdeskhub.users.enums.UserRole;
import lombok.Data;

@Data
public class UserCreateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private UserRole role;
}

