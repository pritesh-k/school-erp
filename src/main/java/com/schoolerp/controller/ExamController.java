package com.schoolerp.controller;

import com.schoolerp.dto.request.ExamCreateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.ExamResponseDto;
import com.schoolerp.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exams")
@RequiredArgsConstructor
public class ExamController {
    private final ExamService service;

    @PostMapping
    public ApiResponse<ExamResponseDto> create(@RequestBody ExamCreateDto dto) {
        return ApiResponse.ok(service.create(dto));
    }

    @GetMapping
    public ApiResponse<List<ExamResponseDto>> list(Pageable pageable) {
        return ApiResponse.paged(service.list(pageable));
    }

    @PutMapping("/{id}")
    public ApiResponse<ExamResponseDto> update(@PathVariable Long id, @RequestBody ExamCreateDto dto) {
        return ApiResponse.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ApiResponse<ExamResponseDto> get(@PathVariable Long id) {
        return ApiResponse.ok(service.get(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok(null);
    }


}