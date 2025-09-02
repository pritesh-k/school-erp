package com.schoolerp.mapper;

import com.schoolerp.dto.request.ExamCreateDto;
import com.schoolerp.dto.response.ExamResponseDto;
import com.schoolerp.entity.Exam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamMapper {
    Exam toEntity(ExamCreateDto dto);
    ExamResponseDto toDto(Exam entity);
    List<ExamResponseDto> toDtoList(List<Exam> entities);

    @Mapping(target = "id", ignore = true) // don't overwrite ID on update
    void updateEntityFromDto(ExamCreateDto dto, @MappingTarget Exam entity);
}
