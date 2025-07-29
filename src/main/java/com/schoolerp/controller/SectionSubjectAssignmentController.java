package com.schoolerp.controller;

import com.schoolerp.dto.request.SectionSubjectAssignmentCreateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.SectionSubjectAssignmentResponseDto;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.impl.SectionSubjectAssignmentService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sections-assignment")
@RequiredArgsConstructor
public class SectionSubjectAssignmentController {

    @Autowired
    private SectionSubjectAssignmentService service;
    @Autowired
    private final RequestContextService requestContextService;

    @PostMapping("/{sectionId}/subjects")
    public ApiResponse<Void> assignSubjectToSection(
            @PathVariable Long sectionId,
            @RequestBody SectionSubjectAssignmentCreateDto dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        service.create(dto, sectionId);
        return ApiResponse.ok(null);
    }

    @GetMapping("/{sectionId}/subjects")
    public ApiResponse<List<SectionSubjectAssignmentResponseDto>> listAllBySectionId(
            @PathVariable Long sectionId,
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(10) Integer size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(service.listBySectionId(sectionId, pageable));
    }
    @DeleteMapping("/{assignmentId}")
    public ApiResponse<Void> assignSubjectToSection(
            @PathVariable Long assignmentId) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        service.delete(assignmentId);
        return ApiResponse.ok(null);
    }
}