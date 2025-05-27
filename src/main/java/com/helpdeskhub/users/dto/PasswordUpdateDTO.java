package com.helpdeskhub.users.dto;

import lombok.Data;

@Data
public class PasswordUpdateDTO {
    private String currentPasswordHash;
    private String newPasswordHash;
}
