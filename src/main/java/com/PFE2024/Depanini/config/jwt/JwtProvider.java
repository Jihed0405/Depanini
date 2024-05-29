package com.PFE2024.Depanini.config.jwt;

import org.springframework.security.core.Authentication;

import com.PFE2024.Depanini.config.UserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtProvider {
    public String generateToken(UserPrincipal auth);

    public Authentication getAuthentication(HttpServletRequest request);

    public boolean isTokenValid(HttpServletRequest request);
}