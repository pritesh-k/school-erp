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
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classes")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService service;
    private final RequestContextService requestContextService;

    @GetMapping
    public ApiResponse<List<ClassResponseDto>> listAllClasses(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(service.list(pageable));
    }
    @GetMapping("/{classId}")
    public ApiResponse<ClassResponseDto> getClassByClassId(@PathVariable @NotNull Long classId) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        return ApiResponse.ok(service.get(classId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ApiResponse<ClassResponseDto> createClass(@RequestBody ClassCreateDto dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        Long userId = userTypeInfo.getUserId();
        return ApiResponse.ok(service.create(dto, userId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{classId}")
    public ApiResponse<ClassResponseDto> updateClassByClassId(
            @PathVariable Long classId,
            @RequestBody ClassCreateDto dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        Long userId = userTypeInfo.getUserId();
        return ApiResponse.ok(service.updateClassOnly(classId, dto, userId));
    }
    @GetMapping("/total")
    public ApiResponse<Long> getTotalClassCount() {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        return ApiResponse.ok(service.getTotalCount());
    }

    // For sections

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{classId}/sections")
    public ApiResponse<SectionResponseDto> addSectionToAClass(@PathVariable @NotNull Long classId, @RequestBody SectionCreateDto dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        Long userId = userTypeInfo.getUserId();
        return ApiResponse.ok(service.addSection(classId, dto, userId));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{classId}/sections")
    public ApiResponse<List<SectionResponseDto>> getAllSectionsOfAClass(
            @PathVariable @NotNull Long classId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size)  {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.Direction.ASC, "name");
        return ApiResponse.paged(service.sectionsByClass(classId, pageable));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{classId}/sections/{sectionId}")
    public ApiResponse<SectionResponseDto> updateSectionOfAClass(
            @PathVariable Long classId,
            @PathVariable Long sectionId,
            @RequestBody SectionUpdateDto dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long userId = userTypeInfo.getUserId();
        return ApiResponse.ok(service.updateSectionsOnly(classId, dto, userId, sectionId));
    }
}