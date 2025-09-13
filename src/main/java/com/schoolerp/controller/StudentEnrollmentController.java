package com.schoolerp.controller;

import com.schoolerp.dto.request.*;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.StudentResponseDto;
import com.schoolerp.dto.response.enrollments.StudentEnrollmentResDto;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.StudentEnrollmentService;
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
@RequestMapping("/api/v1/student-enrollment")
@RequiredArgsConstructor
public class StudentEnrollmentController {

    @Autowired
    private final StudentEnrollmentService enrollmentService;
    @Autowired
    private final RequestContextService requestContextService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<StudentEnrollmentDTO> create(@RequestBody StudentEnrollmentCreateDTO dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long createdByUserId = userTypeInfo.getUserId();
        String academicSession = userTypeInfo.getAcademicSession();
        StudentEnrollmentDTO studentEnrollmentDTO = enrollmentService.create(dto, createdByUserId);
        return ApiResponse.ok(studentEnrollmentDTO);
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<List<StudentEnrollmentDTO>> bulkCreate(
            @RequestBody BulkStudentEnrollmentCreateDTO dto) {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long createdByUserId = userTypeInfo.getUserId();
        String academicSession = userTypeInfo.getAcademicSession();

        List<StudentEnrollmentDTO> enrollments = enrollmentService.bulkCreate(dto, createdByUserId, academicSession);
        return ApiResponse.ok(enrollments);
    }


    @PutMapping("/{enrollmentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<StudentEnrollmentDTO> update(@RequestBody StudentEnrollmentUpdateDTO dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long createdByUserId = userTypeInfo.getUserId();
        String academicSession = userTypeInfo.getAcademicSession();
        StudentEnrollmentDTO studentEnrollmentDTO = enrollmentService.update(dto, createdByUserId, academicSession);
        return ApiResponse.ok(studentEnrollmentDTO);
    }

    @GetMapping("/total")
    public ApiResponse<Long> totalEnrollments(
            @RequestParam String academicSession,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Long studentId) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        return ApiResponse.ok(enrollmentService.getTotalStudentCount(
                academicSession, sectionId, classId, studentId));
    }

    @GetMapping
    public ApiResponse<List<StudentEnrollmentResDto>> getEnrollments(
            @RequestParam String academicSession,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Long studentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<StudentEnrollmentResDto> enrollments = enrollmentService.getEnrollments(
                academicSession, sectionId, classId, studentId, pageable);

        return ApiResponse.paged(enrollments);
    }
}
