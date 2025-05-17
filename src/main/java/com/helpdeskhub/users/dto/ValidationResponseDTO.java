package com.helpdeskhub.users.dto;

import com.helpdeskhub.users.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ValidationResponseDTO {
    private Integer id;
    private UserRole role;
}
