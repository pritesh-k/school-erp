package com.schoolerp.controller;

import com.schoolerp.dto.request.LoginRequest;
import com.schoolerp.dto.request.RegisterRequest;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.AuthResponse;
import com.schoolerp.dto.response.UserDTO;
import com.schoolerp.entity.User;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.enums.Role;
import com.schoolerp.exception.UnauthorizedException;
import com.schoolerp.service.AuthService;
import com.schoolerp.service.RequestContextService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.schoolerp.enums.Role.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final RequestContextService requestContextService;

    @Autowired
    private com.schoolerp.service.StudentService studentService;
    @Autowired
    private com.schoolerp.service.TeacherService teacherService;
    @Autowired
    private com.schoolerp.service.ParentService parentService;

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody RegisterRequest req) {
        UserDTO user = service.register(req);
        return ApiResponse.ok("User registered successfully");
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
    public ApiResponse<?> me(HttpServletRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        Long userId = userTypeInfo.getUserId();
        Role role = userTypeInfo.getUserType();
        Long entityId = userTypeInfo.getEntityId();

        return switch (role) {
            case STUDENT  -> ApiResponse.ok(studentService.get(entityId));
            case TEACHER  -> ApiResponse.ok(teacherService.getByTeacherId(entityId));
            case PARENT   -> ApiResponse.ok(parentService.getByUserId(entityId));
            case ADMIN    -> ApiResponse.ok(userTypeInfo);   // ADMIN / PRINCIPAL
            case PRINCIPAL -> ApiResponse.ok(userTypeInfo);
            default -> throw new UnauthorizedException("Unauthorized access");};
    }
}