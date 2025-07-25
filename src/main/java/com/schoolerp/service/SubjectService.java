package com.schoolerp.service;

import com.schoolerp.dto.request.SubjectCreateDto;
import com.schoolerp.dto.request.SubjectUpdateDto;
import com.schoolerp.dto.response.SubjectResponseDto;
import com.schoolerp.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubjectService {
    SubjectResponseDto create(SubjectCreateDto dto,Long userId);
    SubjectResponseDto get(Long id);
    Page<SubjectResponseDto> list(Pageable pageable);
    SubjectResponseDto update(Long id, SubjectUpdateDto dto, Long userId);
    void delete(Long id);
}