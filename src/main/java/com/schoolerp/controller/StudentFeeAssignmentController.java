package com.schoolerp.controller;

import com.schoolerp.dto.request.FeeAssignDto;
import com.schoolerp.dto.request.FeeAssignUpdateDto;
import com.schoolerp.dto.request.StudentFeeAssignmentRequest;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.StudentFeeAssignmentResponse;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.impl.StudentFeeAssignmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fees")
@Validated
@Slf4j
public class StudentFeeAssignmentController {

    @Autowired
    private StudentFeeAssignmentService assignmentService;

    @Autowired
    private RequestContextService requestContextService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
    @PostMapping("/assignment")
    public ApiResponse<StudentFeeAssignmentResponse> createAssignment(@Valid @RequestBody FeeAssignDto request) {
        log.info("Creating student fee assignment for enrollment: {}", request.getStudentEnrollmentId());
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long createdBy = userTypeInfo.getUserId();
        return ApiResponse.ok(assignmentService.assign(request, createdBy));
    }

//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT') or hasAuthority('TEACHER')")
//    @GetMapping("/assignment")
//    public ApiResponse<List<StudentFeeAssignmentResponse>> getAllAssignments(
//            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
//            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) Integer size,
//            @RequestParam(required = false) Long sessionId,
//            @RequestParam(required = false) Long classId,
//            @RequestParam(required = false) Long studentId) {
//        log.info("Fetching assignments - page: {}, size: {}, sessionId: {}, classId: {}, studentId: {}",
//                page, size, sessionId, classId, studentId);
//        Pageable pageable = PageRequest.of(page, size);
//        Page<StudentFeeAssignmentResponse> results = assignmentService.list(pageable, sessionId, classId, studentId);
//        return ApiResponse.paged(results);
//    }
//
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT') or hasAuthority('TEACHER')")
    @GetMapping("/assignment/student/{studentEnrollId}")
    public ApiResponse<StudentFeeAssignmentResponse> getStudentAssignment(
            @PathVariable Long studentEnrollId) {
        return ApiResponse.ok(assignmentService.getByStudentEnrollment(studentEnrollId));
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
    @PutMapping("/assignment/{id}")
    public ApiResponse<StudentFeeAssignmentResponse> updateAssignment(
            @PathVariable Long id,
            @Valid @RequestBody FeeAssignUpdateDto request) {
        log.info("Updating assignment with id: {}", id);
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long updatedBy = userTypeInfo.getUserId();
        return ApiResponse.ok(assignmentService.assignUpdate(id, request, updatedBy));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/assignment/bulk/{classId}/feeStructure/{feeStructureId}")
    public ApiResponse<Void> bulkAssignFeeStructure(
            @PathVariable Long classId, @PathVariable Long feeStructureId) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long createdBy = userTypeInfo.getUserId();
        String academicSessionName = userTypeInfo.getAcademicSession();
        assignmentService.bulkAssign(classId, feeStructureId, academicSessionName, createdBy);
        return ApiResponse.ok(null);
    }

}
