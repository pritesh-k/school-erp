package com.schoolerp.controller;

import com.schoolerp.dto.request.AssignmentRequest;
import com.schoolerp.dto.request.SectionTeacherAssignmentListDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.AssignmentResponse;
import com.schoolerp.dto.response.TeachingSectionDto;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.impl.SectionTeacherAssignmentService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/assignments")
@RequiredArgsConstructor
public class SectionTeacherAssignmentController {

    private final SectionTeacherAssignmentService service;
    private final RequestContextService requestContextService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/classes/{classId}/sections/{sectionId}/assign-teacher")
    public ApiResponse<Void> assignTeacherToSection(
            @PathVariable Long classId,
            @PathVariable Long sectionId,
            @RequestBody AssignmentRequest request) {
        service.assignTeacher(request, classId, sectionId);
        return ApiResponse.ok(null);
    }

    @GetMapping
    public ApiResponse<List<SectionTeacherAssignmentListDto>> listAllSectionTeacherAssignment(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10")@Min(1) @Max(100) int size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(service.listAll(pageable));
    }

    @GetMapping("/{assignmentId}")
    public ApiResponse<AssignmentResponse> getAssignmentsByAssignmentId(@PathVariable Long assignmentId) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        return ApiResponse.ok(service.getById(assignmentId));
    }

    @GetMapping("/{teacherId}")
    public ApiResponse<List<TeachingSectionDto>> getSectionsAssignedToTeacher(
            @PathVariable Long teacherId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10")@Min(1) @Max(100) int size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(service.getTeachingSections(teacherId, pageable));
    }

    @GetMapping("/section/{sectionId}")
    public ApiResponse<List<AssignmentResponse>> getTeachersAssignedToSection(
            @PathVariable Long sectionId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10")@Min(1) @Max(100) int size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(service.getBySection(sectionId, pageable));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{assignmentId}")
    public ApiResponse<Void> updateAssignment(
            @PathVariable Long assignmentId,
            @RequestBody AssignmentRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        service.update(assignmentId, request);
        return ApiResponse.ok(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{assignmentId}")
    public ApiResponse<Void> deleteByAssignmentId(@PathVariable Long assignmentId) {
        service.delete(assignmentId);
        return ApiResponse.ok(null);
    }
}
