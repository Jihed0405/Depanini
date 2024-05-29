package com.PFE2024.Depanini.model.dto;

import com.PFE2024.Depanini.model.User;
import lombok.Data;

@Data
public class LoginResponse {
    private User user;
    private String token;

    public LoginResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }

    // Getters and setters
}
