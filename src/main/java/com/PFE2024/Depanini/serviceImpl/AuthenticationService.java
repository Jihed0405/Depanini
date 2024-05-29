package com.PFE2024.Depanini.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.PFE2024.Depanini.config.UserPrincipal;
import com.PFE2024.Depanini.config.jwt.JwtProvider;
import com.PFE2024.Depanini.model.dto.*;
import com.PFE2024.Depanini.service.IAuthenticationService;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public LoginResponse signInAndReturnJWT(LoginRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(userPrincipal);
        return new LoginResponse(userPrincipal.getUser(), jwt);
    }
}