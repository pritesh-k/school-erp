package com.schoolerp.controller;

import com.schoolerp.dto.request.SubjectCreateDto;
import com.schoolerp.dto.request.SubjectUpdateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.SubjectResponseDto;
import com.schoolerp.dto.response.TeachersAssignedDto;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.exception.UnauthorizedException;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.SubjectService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService service;

    private final RequestContextService requestContextService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ApiResponse<SubjectResponseDto> create(
            @RequestBody SubjectCreateDto dto,
            HttpServletRequest request) {

        // Extract token from request
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);
        String token = userTypeInfo.getToken();
        if (token != null) {
            Long userId = userTypeInfo.getUserId();
            if (userId == null) {
                throw new UnauthorizedException("Invalid token");
            }
            return ApiResponse.ok(service.create(dto, userId));
        }

        throw new UnauthorizedException("No valid token found");
    }


    @GetMapping
    public ApiResponse<List<SubjectResponseDto>> list(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(service.list(pageable));
    }

    @GetMapping("/{subjectId}")
    public ApiResponse<SubjectResponseDto> getById(@PathVariable Long subjectId) {
        return ApiResponse.ok(service.get(subjectId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{subjectId}")
    public ApiResponse<SubjectResponseDto> updateSubject(
            @PathVariable Long subjectId,
            @RequestBody SubjectUpdateDto dto) {
        return ApiResponse.ok(service.update(subjectId, dto));
    }
}