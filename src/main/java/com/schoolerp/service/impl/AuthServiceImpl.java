package com.schoolerp.service.impl;

import com.schoolerp.dto.request.LoginRequest;
import com.schoolerp.dto.request.RegisterRequest;
import com.schoolerp.dto.response.AuthResponse;
import com.schoolerp.entity.User;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.repository.UserRepository;
import com.schoolerp.security.JwtUtil;
import com.schoolerp.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    @Override
    @Transactional
    public User register(RegisterRequest req) {
        log.info("Registering new user: {} with role: {}", req.username(), req.role());

        // Validate user doesn't exist
        if (repo.existsByUsername(req.username()) || repo.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Username or email already taken");
        }

        // Create User with enhanced fields
        User user = User.builder()
                .username(req.username())
                .email(req.email())
                .password(encoder.encode(req.password()))
                .role(req.role())
                .firstName(req.firstName())
                .lastName(req.lastName())
                .identifier(req.username())
                .entityId(null)  // Will be set later when creating specific entities
                .build();
        user.setActive(true);
        user.setDeleted(false);
        user.createFullName();
        User savedUser = repo.save(user);

        log.info("User registered successfully with ID: {}", savedUser.getId());
        return savedUser;
    }
    @Override
    public AuthResponse login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password())
        );

        User user = (User) auth.getPrincipal();
        UserTypeInfo userTypeInfo = new UserTypeInfo();
        userTypeInfo.setUserId(user.getId());
        userTypeInfo.setUserType(user.getRole().name());
        userTypeInfo.setEntityId(user.getEntityId());
        userTypeInfo.setDisplayName(user.getDisplayName());

        String token = jwtUtil.generateToken(user);
        userTypeInfo.setToken(token); // Set the token in UserTypeInfo

        return new AuthResponse(userTypeInfo);
    }

    @Override
    public AuthResponse refresh(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String oldToken = header.substring(7);
            String username = jwtUtil.extractUsername(oldToken);
            User user = repo.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            UserTypeInfo userTypeInfo = new UserTypeInfo();
            userTypeInfo.setUserId(user.getId());
            userTypeInfo.setUserType(user.getRole().name());
            userTypeInfo.setEntityId(user.getEntityId());
            userTypeInfo.setDisplayName(user.getDisplayName());

            String newToken = jwtUtil.generateToken(user);
            userTypeInfo.setToken(newToken); // Set the new token in UserTypeInfo
            return new AuthResponse(userTypeInfo);
        }
        throw new IllegalArgumentException("Invalid refresh request");
    }
}