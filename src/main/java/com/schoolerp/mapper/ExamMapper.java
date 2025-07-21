package com.schoolerp.mapper;

import com.schoolerp.dto.response.ExamResponseDto;
import com.schoolerp.entity.Exam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamMapper {
    ExamResponseDto toDto(Exam exam);
}