package com.schoolerp.controller;

import com.schoolerp.dto.request.ExamSlotCreateDto;
import com.schoolerp.dto.request.ExamSlotUpdateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.ExamSlotResponseDto;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.service.ExamSlotService;
import com.schoolerp.service.RequestContextService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exam-slots")
@RequiredArgsConstructor
public class ExamSlotController {

    private final ExamSlotService service;
    private final RequestContextService requestContextService;

    @PostMapping("/{examId}")
    public ApiResponse<ExamSlotResponseDto> create(@RequestBody @Valid ExamSlotCreateDto dto, @PathVariable Long examId) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long createdById = userTypeInfo.getUserId();
        return ApiResponse.ok(service.create(dto, examId, createdById));
    }

    @GetMapping("/{id}")
    public ApiResponse<ExamSlotResponseDto> get(@PathVariable Long id) {
        return ApiResponse.ok(service.getById(id));
    }

    @GetMapping
    public ApiResponse<List<ExamSlotResponseDto>> list(
            @RequestParam Long examId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ApiResponse.paged(service.list(examId, pageable));
    }

    @PutMapping("/{id}")
    public ApiResponse<ExamSlotResponseDto> update(@PathVariable Long id, @RequestBody @Valid ExamSlotUpdateDto dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long updatedById = userTypeInfo.getUserId();
        return ApiResponse.ok(service.update(id, dto, updatedById));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok(null);
    }
}

