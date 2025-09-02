package com.schoolerp.service;

import com.schoolerp.dto.request.ExamSlotCreateDto;
import com.schoolerp.dto.request.ExamSlotUpdateDto;
import com.schoolerp.dto.response.ExamSlotResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExamSlotService {
    ExamSlotResponseDto create(ExamSlotCreateDto dto, Long examId, Long createById);
    ExamSlotResponseDto update(Long id, ExamSlotUpdateDto dto, Long updatedById);
    void delete(Long id);
    ExamSlotResponseDto getById(Long id);
    Page<ExamSlotResponseDto> list(Long examId, Pageable pageable);
}

