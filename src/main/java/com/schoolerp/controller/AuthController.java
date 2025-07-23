package com.schoolerp.controller;

import com.schoolerp.dto.request.LoginRequest;
import com.schoolerp.dto.request.RegisterRequest;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.AuthResponse;
import com.schoolerp.dto.response.StudentResponseDto;
import com.schoolerp.dto.response.TeacherResponseDto;
import com.schoolerp.entity.Parent;
import com.schoolerp.entity.User;
import com.schoolerp.exception.UnauthorizedException;
import com.schoolerp.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @Autowired
    private com.schoolerp.service.StudentService studentService;
    @Autowired
    private com.schoolerp.service.TeacherService teacherService;
    @Autowired
    private com.schoolerp.service.ParentService parentService;

    @PostMapping("/register")
    public ApiResponse<Object> register(@RequestBody RegisterRequest req) {
        User user = service.register(req);
        return ApiResponse.ok(user);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest req) {
        return ApiResponse.ok(service.login(req));
    }

    @PostMapping("/refresh-token")
    public ApiResponse<AuthResponse> refresh(HttpServletRequest request) {
        return ApiResponse.ok(service.refresh(request));
    }

    @GetMapping("/me")
    public ApiResponse<?> me(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return switch (user.getRole()) {
            case STUDENT  -> ApiResponse.ok(studentService.get(user.getEntityId()));
            case TEACHER  -> ApiResponse.ok(teacherService.getByTeacherId(user.getEntityId()));
            case PARENT   -> ApiResponse.ok(parentService.getByUserId(user.getEntityId()));
            case ADMIN    -> ApiResponse.ok(user);   // ADMIN / PRINCIPAL
            case PRINCIPAL -> ApiResponse.ok(user);
            default -> throw new UnauthorizedException("Unauthorized access");};
    }
}