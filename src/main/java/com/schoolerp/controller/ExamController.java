package com.schoolerp.controller;

import com.schoolerp.dto.request.ExamCreateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.ExamResponseDto;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.service.ExamService;
import com.schoolerp.service.RequestContextService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exams")
@RequiredArgsConstructor
public class ExamController {
    private final ExamService service;
    private final RequestContextService requestContextService;

    @PostMapping
    public ApiResponse<ExamResponseDto> create(@RequestBody ExamCreateDto dto, HttpServletRequest request) {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);

        Long userId = userTypeInfo.getUserId();
        Long entityId = userTypeInfo.getEntityId();
        String role = userTypeInfo.getUserType().name();
        return ApiResponse.ok(service.create(dto, userId, entityId, role));
    }

    @GetMapping
    public ApiResponse<List<ExamResponseDto>> list(
            @RequestParam(required = false, defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) int size, HttpServletRequest request) {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());

        Long userId = userTypeInfo.getUserId();
        Long entityId = userTypeInfo.getEntityId();
        String role = userTypeInfo.getUserType().name();
        return ApiResponse.paged(service.list(pageable, userId, entityId, role));
    }

    @PutMapping("/{examId}")
    public ApiResponse<ExamResponseDto> update(
            @PathVariable Long examId,
            @RequestBody ExamCreateDto dto, HttpServletRequest request) {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);

        Long userId = userTypeInfo.getUserId();
        Long entityId = userTypeInfo.getEntityId();
        String role = userTypeInfo.getUserType().name();
        return ApiResponse.ok(service.update(examId, dto, userId, entityId, role));
    }

    @GetMapping("/{examId}")
    public ApiResponse<ExamResponseDto> get(@PathVariable Long examId, HttpServletRequest request) {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);

        Long userId = userTypeInfo.getUserId();
        Long entityId = userTypeInfo.getEntityId();
        String role = userTypeInfo.getUserType().name();
        return ApiResponse.ok(service.get(examId, userId, entityId, role));
    }

    @DeleteMapping("/{examId}")
    public ApiResponse<String> delete(@PathVariable Long examId, HttpServletRequest request) {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);

        Long userId = userTypeInfo.getUserId();
        Long entityId = userTypeInfo.getEntityId();
        String role = userTypeInfo.getUserType().name();
        service.delete(examId, userId, entityId, role);
        return ApiResponse.ok(null);
    }
}