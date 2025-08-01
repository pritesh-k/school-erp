package com.schoolerp.service;

import com.schoolerp.dto.request.TeacherCreateDto;
import com.schoolerp.dto.request.TeacherUpdateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.TeacherResponseDto;
import com.schoolerp.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherService {
    TeacherResponseDto create(TeacherCreateDto dto, Long userId);
    TeacherResponseDto getByTeacherId(Long id);
    Page<TeacherResponseDto> list(Pageable pageable);
    TeacherResponseDto update(Long teacherId, TeacherUpdateDto dto, Long userId);
    void delete(Long id);

    void existsByIdAndUser_Id(Long teacherId, Long userId);

    Long getTotalCount();
}