package com.schoolerp.controller;

import com.schoolerp.dto.request.TeacherCreateDto;
import com.schoolerp.dto.request.TeacherUpdateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.TeacherResponseDto;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.TeacherService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService service;
    private final RequestContextService requestContextService;

    /** * Creates a new teacher.
     * Only accessible by ADMIN role.
     *
     * @param dto the data transfer object containing teacher details
     * @return ApiResponse with the created TeacherResponseDto
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ApiResponse<TeacherResponseDto> create(@RequestBody TeacherCreateDto dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long userId = userTypeInfo.getUserId();
        return ApiResponse.ok(service.create(dto, userId));
    }
    /** * Updates the basic information of a teacher.
     * Only accessible by ADMIN role.
     *
     * @param dto the data transfer object containing updated teacher details
     * @return ApiResponse with the updated TeacherResponseDto
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{teacherId}")
    public ApiResponse<TeacherResponseDto> updateTeacherBasics(@PathVariable Long teacherId, @RequestBody TeacherUpdateDto dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long userId = userTypeInfo.getUserId();
        return ApiResponse.ok(service.update(teacherId, dto, userId));
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('PRINCIPAL')")
    @GetMapping
    public ApiResponse<List<TeacherResponseDto>> list(
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) Integer size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(service.list(pageable));
    }

    /** * Retrieves a teacher by their ID.
     * Accessible by ADMIN, TEACHER, and PRINCIPAL roles.
     *
     * @param id the ID of the teacher to retrieve
     * @return ApiResponse with the teacher's details
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('PRINCIPAL')")
    @GetMapping("/{id}")
    public ApiResponse<TeacherResponseDto> getById(@PathVariable Long id) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        return ApiResponse.ok(service.getByTeacherId(id));
    }

    @GetMapping("/total")
    public ApiResponse<Long> getTotalTeachersCount() {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        return ApiResponse.ok(service.getTotalCount());
    }


}