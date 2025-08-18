package com.schoolerp.controller;

import com.schoolerp.dto.request.TeacherSubjectAssignmentCreateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.TeacherSubjectAssignmentResponseDto;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.impl.TeacherSubjectAssignmentService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher-section-subject-assignments")
@RequiredArgsConstructor
public class TeacherSubjectAssignmentController {

    @Autowired
    private TeacherSubjectAssignmentService service;

    @Autowired
    private final RequestContextService requestContextService;


    @PostMapping("/teacherId/{teacherId}")
    public ApiResponse<Void> assignSectionSubjectToTeacher(
            @PathVariable Long teacherId,
            @RequestBody TeacherSubjectAssignmentCreateDto dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        service.create(dto, teacherId, userTypeInfo.getAcademicSession());
        return ApiResponse.ok(null);
    }

    @GetMapping("/byTeacherId/{teacherId}")
    public ApiResponse<List<TeacherSubjectAssignmentResponseDto>> listByTeacher(
            @PathVariable Long teacherId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(10) int size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        String academicSession = userTypeInfo.getAcademicSession();
        Page<TeacherSubjectAssignmentResponseDto> result =
                service.listByTeacher(teacherId, PageRequest.of(page, size), academicSession);
        return ApiResponse.paged(result);
    }

    @GetMapping("/{sectionSubjectAssignmentId}")
    public ApiResponse<List<TeacherSubjectAssignmentResponseDto>> listBySectionSubjectAssignmentId(
            @PathVariable Long sectionSubjectAssignmentId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(10) int size) {
        requestContextService.getCurrentUserContext();
        return ApiResponse.paged(service.listBySectionSubjectAssignmentId(sectionSubjectAssignmentId, PageRequest.of(page, size)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
