package com.schoolerp.mapper;

import com.schoolerp.dto.response.ExamResultResponseDto;
import com.schoolerp.entity.ExamResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamResultMapper {
    ExamResultResponseDto toDto(ExamResult entity);
}