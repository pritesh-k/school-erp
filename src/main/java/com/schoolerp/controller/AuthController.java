package com.schoolerp.controller;

import com.schoolerp.dto.request.LoginRequest;
import com.schoolerp.dto.request.RegisterRequest;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.AuthResponse;
import com.schoolerp.entity.User;
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

//    @GetMapping("/me")
//    public ApiResponse<?> me(Authentication auth) {
//        User user = (User) auth.getPrincipal();
//        return switch (user.getRole()) {
//            case STUDENT  -> studentService.getByUserId(user.getEntityId());
//            case TEACHER  -> teacherService.getByUserId(user.getEntityId());
//            case PARENT   -> parentService.getByUserId(user.getEntityId());
//            default       -> user;   // ADMIN / PRINCIPAL
//        };
//    }
}