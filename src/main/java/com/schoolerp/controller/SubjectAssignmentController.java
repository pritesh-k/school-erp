package com.schoolerp.controller;

import com.schoolerp.dto.request.SubjectAssignmentCreateDto;
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
    public ApiResponse<SubjectAssignmentResponseDto> create(@RequestBody SubjectAssignmentCreateDto dto) {
        UserTypeInfo user = requestContextService.getCurrentUserContext();
        return ApiResponse.ok(service.create(dto, user.getUserId()));
    }

    @GetMapping
    public ApiResponse<List<SubjectAssignmentResponseDto>> list(
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(service.list(pageable));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok(null);
    }
}
