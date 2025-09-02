package com.schoolerp.mapper;

import com.schoolerp.dto.request.ExamResultCreateDto;
import com.schoolerp.dto.response.ExamResultResponseDto;
import com.schoolerp.entity.ExamResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamResultMapper {
    ExamResult toEntity(ExamResultCreateDto dto);
    ExamResultResponseDto toDto(ExamResult entity);
    List<ExamResultResponseDto> toDtoList(List<ExamResult> entities);
}