package com.schoolerp.controller;

import com.schoolerp.dto.request.ClassTeacherAssignmentRequest;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.ClassTeacherAssignmentResponse;
import com.schoolerp.entity.ClassTeacherAssignment;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.impl.ClassTeacherAssignmentService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/class-teachers")
@RequiredArgsConstructor
public class ClassTeacherAssignmentController {

    private final ClassTeacherAssignmentService service;

    private final RequestContextService requestContextService;

    @PostMapping("/assign")
    public ApiResponse<ClassTeacherAssignmentResponse> assignClassTeacher(@RequestBody ClassTeacherAssignmentRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long createdByUserId = userTypeInfo.getUserId();
        String academicSessionName = userTypeInfo.getAcademicSession();
        return ApiResponse.ok(service.assignClassTeacher(request, academicSessionName, createdByUserId));
    }

    @GetMapping("/section/{sectionId}")
    public ApiResponse<ClassTeacherAssignmentResponse> getClassTeacherBySection(
            @PathVariable Long sectionId) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long createdByUserId = userTypeInfo.getUserId();
        String academicSessionName = userTypeInfo.getAcademicSession();

        return ApiResponse.ok(service.getClassTeacherBySection(sectionId, academicSessionName));
    }

    @GetMapping("/assignments")
    public ApiResponse<List<ClassTeacherAssignmentResponse>> getTeacherAssignments(
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(10) Integer size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Pageable pageable = PageRequest.of(page, size);
        String academicSessionName = userTypeInfo.getAcademicSession();
        return ApiResponse.paged(service.getTeacherAssignments(pageable,teacherId, academicSessionName));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> removeAssignment(@PathVariable Long id) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        Long removedById = userTypeInfo.getUserId();
//        String academicSessionName = userTypeInfo.getAcademicSession();
        service.removeAssignment(id);
        return ApiResponse.ok(null );
    }
}

