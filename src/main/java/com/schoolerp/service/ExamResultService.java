package com.schoolerp.service;

import com.schoolerp.dto.request.ExamResultCreateDto;
import com.schoolerp.dto.response.ExamResultResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExamResultService {
    ExamResultResponseDto create(ExamResultCreateDto dto);
    ExamResultResponseDto get(Long id);
    Page<ExamResultResponseDto> list(Pageable pageable);
    ExamResultResponseDto update(Long id, ExamResultCreateDto dto);
    void delete(Long id);
}