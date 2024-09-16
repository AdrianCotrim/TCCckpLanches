package com.fiec.ckplanches.DTO;

import com.fiec.ckplanches.model.user.UserRole;

public record UserTableDTO(Integer userId, String username, String userEmail, UserRole role) {
}
