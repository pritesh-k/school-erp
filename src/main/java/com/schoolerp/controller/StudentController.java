package com.schoolerp.controller;

import com.schoolerp.dto.request.StudentCreateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.StudentResponseDto;
import com.schoolerp.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<StudentResponseDto> create(@RequestBody StudentCreateDto dto) {
        return ApiResponse.ok(service.create(dto));
    }

    @GetMapping
    public ApiResponse<List<StudentResponseDto>> list(Pageable p) {
        return ApiResponse.paged(service.list(p));
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentResponseDto> get(@PathVariable Long id) {
        return ApiResponse.ok(service.get(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<StudentResponseDto> update(@PathVariable Long id, @RequestBody StudentCreateDto dto) {
        return ApiResponse.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok(null);
    }

    @PostMapping("/bulk-upload")
    public ApiResponse<Void> bulk(@RequestParam("file") MultipartFile file) {
        service.bulkUpload(file);
        return ApiResponse.ok(null);
    }
}