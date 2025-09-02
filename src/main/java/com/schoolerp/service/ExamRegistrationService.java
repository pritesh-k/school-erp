package com.schoolerp.service;

import com.schoolerp.dto.request.ExamRegistrationCreateDto;
import com.schoolerp.dto.response.ExamRegistrationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExamRegistrationService {
    ExamRegistrationResponseDto create(ExamRegistrationCreateDto dto);
    void delete(Long id);
    ExamRegistrationResponseDto getById(Long id);
    Page<ExamRegistrationResponseDto> list(Pageable pageable);
}

