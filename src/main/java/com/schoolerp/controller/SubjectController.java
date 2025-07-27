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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService service;

    @Autowired
    private final RequestContextService requestContextService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ApiResponse<SubjectResponseDto> create(
            @RequestBody SubjectCreateDto dto) {

        // Extract token from request
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        Long userId = userTypeInfo.getUserId();
        return ApiResponse.ok(service.create(dto, userId));
    }


    @GetMapping
    public ApiResponse<List<SubjectResponseDto>> list(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        Pageable pageable = PageRequest.of(page, size).withSort(Sort.Direction.ASC, "code");
        return ApiResponse.paged(service.list(pageable));
    }

    @GetMapping("/{subjectId}")
    public ApiResponse<SubjectResponseDto> getById(@PathVariable Long subjectId) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        return ApiResponse.ok(service.get(subjectId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{subjectId}")
    public ApiResponse<SubjectResponseDto> updateSubject(
            @PathVariable Long subjectId,
            @RequestBody SubjectUpdateDto dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        return ApiResponse.ok(service.update(subjectId, dto, userTypeInfo.getUserId()));
    }
}