package com.schoolerp.controller;

import com.schoolerp.dto.request.AssignmentRequest;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.AssignmentResponse;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.impl.SectionTeacherAssignmentService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/assignments")
@RequiredArgsConstructor
public class SectionTeacherAssignmentController {

    private final SectionTeacherAssignmentService service;
    private final RequestContextService requestContextService;

    @PostMapping("/classes/{classId}/sections/{sectionId}/assign-teacher")
    public ApiResponse<AssignmentResponse> assign(@PathVariable Long classId, @PathVariable Long sectionId, @RequestBody AssignmentRequest request) {
        return ApiResponse.ok(service.assignTeacher(request, classId, sectionId));
    }

    @GetMapping
    public ApiResponse<List<AssignmentResponse>> listAll(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10")@Min(1) @Max(100) int size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(service.listAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssignmentResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AssignmentResponse>> update(@PathVariable Long id,
                                                                  @RequestBody AssignmentRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
