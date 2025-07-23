package com.schoolerp.service;

import com.schoolerp.dto.request.ExamCreateDto;
import com.schoolerp.dto.response.ExamResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExamService {
    ExamResponseDto create(ExamCreateDto dto, Long userId, Long entityId, String role);
    ExamResponseDto get(Long id, Long userId, Long entityId, String role);
    Page<ExamResponseDto> list(Pageable pageable, Long userId, Long entityId, String role);
    ExamResponseDto update(Long id, ExamCreateDto dto, Long userId, Long entityId, String role);
    void delete(Long id, Long userId, Long entityId, String role);
}