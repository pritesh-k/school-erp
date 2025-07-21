package com.schoolerp.controller;

import com.schoolerp.dto.request.FeeTypeCreateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.FeeTypeResponseDto;
import com.schoolerp.service.FeeTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fee-types")
@RequiredArgsConstructor
public class FeeTypeController {
    private final FeeTypeService service;

    @PostMapping
    public ApiResponse<FeeTypeResponseDto> create(@RequestBody FeeTypeCreateDto dto) {
        return ApiResponse.ok(service.create(dto));
    }
}