package com.schoolerp.controller;

import com.schoolerp.dto.request.ExamInvigilationDutyCreateDto;
import com.schoolerp.dto.request.ExamInvigilationDutyUpdateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.ExamInvigilationDutyResponseDto;
import com.schoolerp.service.ExamInvigilationDutyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/exam-invigilation-duties")
@RequiredArgsConstructor
public class ExamInvigilationDutyController {

    private final ExamInvigilationDutyService service;

    @PostMapping
    public ApiResponse<ExamInvigilationDutyResponseDto> create(@RequestBody @Valid ExamInvigilationDutyCreateDto dto) {
        return ApiResponse.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ApiResponse<ExamInvigilationDutyResponseDto> get(@PathVariable Long id) {
        return ApiResponse.ok(service.getById(id));
    }

    @GetMapping
    public ApiResponse<Page<ExamInvigilationDutyResponseDto>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ApiResponse.ok(service.list(pageable));
    }

    @PutMapping("/{id}")
    public ApiResponse<ExamInvigilationDutyResponseDto> update(@PathVariable Long id, @RequestBody @Valid ExamInvigilationDutyUpdateDto dto) {
        return ApiResponse.ok(service.updated(id, dto));
    }

//    @DeleteMapping("/{id}")
//    public ApiResponse<Void> delete(@PathVariable Long id) {
//        service.delete(id);
//        return ApiResponse.ok(null);
//    }
}

