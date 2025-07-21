package com.schoolerp.service;

import com.schoolerp.dto.request.LoginRequest;
import com.schoolerp.dto.request.RegisterRequest;
import com.schoolerp.dto.response.AuthResponse;
import com.schoolerp.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    User register(RegisterRequest req);
    AuthResponse login(LoginRequest req);
    AuthResponse refresh(HttpServletRequest request);

}