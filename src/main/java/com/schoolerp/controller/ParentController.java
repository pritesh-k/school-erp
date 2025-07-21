package com.schoolerp.controller;

import com.schoolerp.dto.request.ParentCreateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.ParentResponseDto;
import com.schoolerp.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parents")
@RequiredArgsConstructor
public class ParentController {
    private final ParentService service;

    @PostMapping
    public ApiResponse<ParentResponseDto> create(@RequestBody ParentCreateDto dto) {
        return ApiResponse.ok(service.create(dto));
    }

    @GetMapping
    public ApiResponse<List<ParentResponseDto>> list(Pageable p) {
        return ApiResponse.paged(service.list(p));
    }
}