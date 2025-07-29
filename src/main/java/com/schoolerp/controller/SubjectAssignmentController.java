package com.schoolerp.controller;

import com.schoolerp.dto.request.SubjectAssignmentCreateDto;
import com.schoolerp.dto.request.SubjectAssignmentUpdateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.SubjectAssignmentResponseDto;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.impl.SubjectAssignmentService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/subject-assignments")
@RequiredArgsConstructor
public class SubjectAssignmentController {

    private final SubjectAssignmentService service;
    private final RequestContextService requestContextService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> createAssignment(@RequestBody SubjectAssignmentCreateDto dto) {
        UserTypeInfo user = requestContextService.getCurrentUserContext();
        service.create(dto, user.getUserId());
        return ApiResponse.ok("Subject assignment created successfully.");
    }
    @PutMapping
    public ApiResponse<SubjectAssignmentResponseDto> update(@RequestBody SubjectAssignmentUpdateDto dto) {
        Long userId = requestContextService.getCurrentUserContext().getUserId();
        return ApiResponse.ok(service.update(dto, userId));
    }
    @GetMapping
    public ApiResponse<List<SubjectAssignmentResponseDto>> listAllSubjectAssignments(
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(service.list(pageable));
    }
    @GetMapping("/teacher/{teacherId}")
    public ApiResponse<List<SubjectAssignmentResponseDto>> byTeacher(
            @PathVariable Long teacherId,
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(service.listByTeacher(teacherId, pageable));
    }
    @GetMapping("/section/{sectionId}")
    public ApiResponse<List<SubjectAssignmentResponseDto>> bySection(
            @PathVariable Long sectionId,@RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(service.listBySection(sectionId, pageable));
    }
    @GetMapping("/subject/{subjectId}")
    public ApiResponse<List<SubjectAssignmentResponseDto>> bySubject(
            @PathVariable Long subjectId,
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(service.listBySubject(subjectId, pageable));
    }
    @DeleteMapping("/{subjectAssignmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteBySubjectAssignmentId(@PathVariable Long subjectAssignmentId) {
        service.delete(subjectAssignmentId);
        return ApiResponse.ok(null);
    }
}
