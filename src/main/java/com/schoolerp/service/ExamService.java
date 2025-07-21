package com.schoolerp.service;

import com.schoolerp.dto.request.ExamCreateDto;
import com.schoolerp.dto.response.ExamResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExamService {
    ExamResponseDto create(ExamCreateDto dto);
    ExamResponseDto get(Long id);
    Page<ExamResponseDto> list(Pageable pageable);
    ExamResponseDto update(Long id, ExamCreateDto dto);
    void delete(Long id);
}