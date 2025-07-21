package com.schoolerp.controller;

import com.schoolerp.dto.request.ExamResultCreateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.ExamResultResponseDto;
import com.schoolerp.service.ExamResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exam-results")
@RequiredArgsConstructor
public class ExamResultController {
    private final ExamResultService service;

    @PostMapping
    public ApiResponse<ExamResultResponseDto> create(@RequestBody ExamResultCreateDto dto) {
        return ApiResponse.ok(service.create(dto));
    }
}