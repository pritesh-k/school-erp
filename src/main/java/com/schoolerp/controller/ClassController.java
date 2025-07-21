package com.schoolerp.controller;

import com.schoolerp.dto.request.ClassCreateDto;
import com.schoolerp.dto.request.ClassUpdateDto;
import com.schoolerp.dto.request.SectionCreateDto;
import com.schoolerp.dto.request.SectionUpdateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.ClassResponseDto;
import com.schoolerp.dto.response.SectionResponseDto;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.exception.UnauthorizedException;
import com.schoolerp.service.ClassService;
import com.schoolerp.service.RequestContextService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classes")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService service;
    private final RequestContextService requestContextService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ApiResponse<ClassResponseDto> create(@RequestBody ClassCreateDto dto, HttpServletRequest request) {
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{classId}/sections")
    public ApiResponse<SectionResponseDto> addSection(@PathVariable @NotNull Long classId, @RequestBody SectionCreateDto dto, HttpServletRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);
        String token = userTypeInfo.getToken();
        if (token != null) {
            Long userId = userTypeInfo.getUserId();
            if (userId == null) {
                throw new UnauthorizedException("Invalid token");
            }
            return ApiResponse.ok(service.addSection(classId, dto, userId));
        }

        throw new UnauthorizedException("No valid token found");
    }

    @GetMapping("/{classId}/sections")
    public ApiResponse<List<SectionResponseDto>> sections(@PathVariable @NotNull Long classId) {
        return ApiResponse.ok(service.sectionsByClass(classId));
    }

    @GetMapping("/{classId}")
    public ApiResponse<ClassResponseDto> getClass(@PathVariable @NotNull Long classId) {
        return ApiResponse.ok(service.get(classId));
    }

    @GetMapping
    public ApiResponse<List<ClassResponseDto>> list(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(service.list(pageable));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{classId}")
    public ApiResponse<ClassResponseDto> updateClass(
            @PathVariable Long classId,
            @RequestBody ClassCreateDto dto, HttpServletRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);
        String token = userTypeInfo.getToken();
        if (token != null) {
            Long userId = userTypeInfo.getUserId();
            if (userId == null) {
                throw new UnauthorizedException("Invalid token");
            }
            return ApiResponse.ok(service.updateClassOnly(classId, dto, userId));
        }

        throw new UnauthorizedException("No valid token found");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{classId}/sections/{sectionId}")
    public ApiResponse<SectionResponseDto> updateSection(
            @PathVariable Long classId,
            @PathVariable Long sectionId,
            @RequestBody SectionUpdateDto dto, HttpServletRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);
        String token = userTypeInfo.getToken();
        if (token != null) {
            Long userId = userTypeInfo.getUserId();
            if (userId == null) {
                throw new UnauthorizedException("Invalid token");
            }
            return ApiResponse.ok(service.updateSectionsOnly(classId, dto, userId, sectionId));
        }

        throw new UnauthorizedException("No valid token found");
    }
}