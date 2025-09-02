package com.schoolerp.service;

import com.schoolerp.dto.request.ExamCreateDto;
import com.schoolerp.dto.request.ExamUpdateDto;
import com.schoolerp.dto.response.ExamResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface ExamService {
    ExamResponseDto create(ExamCreateDto dto, Long userId, Long entityId, String role, String academicSession);
    ExamResponseDto get(Long id, Long userId, Long entityId, String role);
    Page<ExamResponseDto> list(Pageable pageable, Long userId, Long entityId, String academicSession);
    ExamResponseDto update(Long id, ExamUpdateDto dto, Long userId, Long entityId, String role);

    ExamResponseDto publishExam(Long id, Long publishedById);

    void delete(Long id, Long userId, Long entityId, String role);
}