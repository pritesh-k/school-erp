package com.schoolerp.controller;

import com.schoolerp.dto.request.ExamResultCreateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.ExamResultResponseDto;
import com.schoolerp.service.ExamResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exam-results")
@RequiredArgsConstructor
public class ExamResultController {
    private final ExamResultService service;

    @PostMapping
    public ApiResponse<ExamResultResponseDto> create(@RequestBody @Valid ExamResultCreateDto dto) {
        return ApiResponse.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ApiResponse<ExamResultResponseDto> get(@PathVariable Long id) {
        return ApiResponse.ok(service.get(id));
    }

    @GetMapping
    public ApiResponse<Page<ExamResultResponseDto>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ApiResponse.ok(service.list(pageable));
    }

    @PutMapping("/{id}")
    public ApiResponse<ExamResultResponseDto> update(@PathVariable Long id, @RequestBody @Valid ExamResultCreateDto dto) {
        return ApiResponse.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok(null);
    }
}