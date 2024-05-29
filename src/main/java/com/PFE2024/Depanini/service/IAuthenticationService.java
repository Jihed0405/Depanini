package com.PFE2024.Depanini.service;

import com.PFE2024.Depanini.model.dto.LoginRequest;
import com.PFE2024.Depanini.model.dto.*;

public interface IAuthenticationService {
    public LoginResponse signInAndReturnJWT(LoginRequest signInRequest);

}
