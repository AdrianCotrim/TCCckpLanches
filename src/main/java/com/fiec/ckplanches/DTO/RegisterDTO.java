package com.fiec.ckplanches.DTO;

import com.fiec.ckplanches.model.user.UserRole;

public record RegisterDTO(String username, String userPassword, String userEmail, UserRole role) {
}
