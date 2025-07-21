package com.schoolerp.controller;

import com.schoolerp.dto.request.FeeRecordCreateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.FeeRecordResponseDto;
import com.schoolerp.service.FeeRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fee-records")
@RequiredArgsConstructor
public class FeeRecordController {
    private final FeeRecordService service;

    @PostMapping
    public ApiResponse<FeeRecordResponseDto> create(@RequestBody FeeRecordCreateDto dto) {
        return ApiResponse.ok(service.create(dto));
    }

    @GetMapping("/student/{id}")
    public ApiResponse<?> byStudent(@PathVariable Long id) {
        return ApiResponse.ok(service.byStudent(id));
    }
}