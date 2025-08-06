package com.schoolerp.controller;

import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.newdtos.academic.AcademicSessionCreateDto;
import com.schoolerp.newdtos.academic.AcademicSessionResponseDto;
import com.schoolerp.newdtos.academic.AcademicSessionUpdateDto;
import com.schoolerp.service.impl.AcademicSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/academic-sessions")
@RequiredArgsConstructor
public class AcademicSessionController {
    private final AcademicSessionService service;

    @GetMapping
    public ApiResponse<List<AcademicSessionResponseDto>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AcademicSessionResponseDto> sessions = service.list(pageable);
        return ApiResponse.paged(sessions);
    }

    @GetMapping("/{id}")
    public ApiResponse<AcademicSessionResponseDto> getById(@PathVariable Long id) {
        return ApiResponse.ok(service.get(id));
    }

    @GetMapping("/active")
    public ApiResponse<AcademicSessionResponseDto> getByActiveStatus() {
        return ApiResponse.ok(service.getActive());
    }

    @PostMapping
    public ApiResponse<AcademicSessionResponseDto> create(@RequestBody @Valid AcademicSessionCreateDto dto) {
        return ApiResponse.ok(service.create(dto));
    }

    @PutMapping("/{sessionId}")
    public ApiResponse<AcademicSessionResponseDto> update(
            @PathVariable Long sessionId, @RequestBody @Valid AcademicSessionUpdateDto dto) {
        return ApiResponse.ok(service.update(sessionId, dto));
    }

    @PutMapping("/{sessionId}/activate")
    public ApiResponse<AcademicSessionResponseDto> activateSession(
            @PathVariable Long sessionId) {
        return ApiResponse.ok(service.activateSession(sessionId));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok(null);
    }
}
