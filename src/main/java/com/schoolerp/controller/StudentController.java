package com.schoolerp.controller;

import com.schoolerp.dto.request.StudentCreateDto;
import com.schoolerp.dto.request.StudentUpdateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.ParentResponseDto;
import com.schoolerp.dto.response.StudentDetailedResponseDto;
import com.schoolerp.dto.response.StudentResponseDto;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.service.ParentService;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.StudentService;
import com.schoolerp.utils.BulkImportReport;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService service;
    private final RequestContextService requestContextService;
    private final ParentService parentService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<StudentResponseDto> create(@RequestBody StudentCreateDto dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long createdByUserId = userTypeInfo.getUserId();
        return ApiResponse.ok(service.create(dto, createdByUserId));
    }

    @PostMapping("/bulk-import")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<BulkImportReport> bulkImport(@RequestParam("file") MultipartFile file) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long createdByUserId = userTypeInfo.getUserId();

        BulkImportReport report = service.bulkImport(file, createdByUserId);
        return ApiResponse.ok(report);
    }

    @GetMapping
    public ApiResponse<List<StudentResponseDto>> list(
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) Integer size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.Direction.ASC, "admissionNumber");
        return ApiResponse.paged(service.list(pageable));
    }

    @GetMapping("/search")
    public ApiResponse<List<StudentResponseDto>> searchStudents(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) Integer size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.Direction.ASC, "admissionNumber");

        Page<StudentResponseDto> result = service.searchStudentsByName(name, pageable);
        return ApiResponse.paged(result);
    }
    @GetMapping("/total")
    public ApiResponse<Long> totalStudents() {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        return ApiResponse.ok(service.getTotalStudentCount());
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentResponseDto> get(@PathVariable Long id) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        return ApiResponse.ok(service.get(id));
    }

    @GetMapping("/detailed/{id}")
    public ApiResponse<StudentDetailedResponseDto> getDetailedStudents(@PathVariable Long id) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        return ApiResponse.ok(service.getDetailed(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<StudentResponseDto> update(@PathVariable Long id, @RequestBody StudentUpdateDto dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long userId = userTypeInfo.getUserId();
        return ApiResponse.ok(service.update(id, dto));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long userId = userTypeInfo.getUserId();
        service.delete(id);
        return ApiResponse.ok(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/bulk-upload")
    public ApiResponse<Void> bulk(@RequestParam("file") MultipartFile file) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long userId = userTypeInfo.getUserId();
        service.bulkUpload(file);
        return ApiResponse.ok(null);
    }

    @GetMapping("/parents")
    public ApiResponse<List<ParentResponseDto>> listParents(
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) Integer size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(parentService.list(pageable));
    }
}