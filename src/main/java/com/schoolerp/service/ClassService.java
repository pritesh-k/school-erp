package com.schoolerp.service;

import com.schoolerp.dto.request.ClassCreateDto;
import com.schoolerp.dto.request.ClassUpdateDto;
import com.schoolerp.dto.request.SectionCreateDto;
import com.schoolerp.dto.request.SectionUpdateDto;
import com.schoolerp.dto.response.ClassResponseDto;
import com.schoolerp.dto.response.SectionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClassService {
    ClassResponseDto create(ClassCreateDto dto, Long userId);
    ClassResponseDto get(Long id);
    Page<ClassResponseDto> list(Pageable pageable);
    ClassResponseDto updateClassOnly(Long classId, ClassCreateDto dto, Long userId);
    void delete(Long id);

    SectionResponseDto addSection(Long classId, SectionCreateDto dto, Long userId);
    List<SectionResponseDto> sectionsByClass(Long classId);

    SectionResponseDto updateSectionsOnly(Long classId, SectionUpdateDto dto, Long userId, Long sectionId);
}