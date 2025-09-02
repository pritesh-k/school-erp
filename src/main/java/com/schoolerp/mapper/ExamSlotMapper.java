package com.schoolerp.mapper;

import com.schoolerp.dto.request.ExamSlotCreateDto;
import com.schoolerp.dto.response.ExamSlotResponseDto;
import com.schoolerp.entity.ExamSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamSlotMapper {
    ExamSlot toEntity(ExamSlotCreateDto dto);

    @Mapping(source = "exam.id", target = "examId")
    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "subject.code", target = "subjectName")
    @Mapping(source = "schoolClass.name", target = "className")
    @Mapping(source = "section.name", target = "sectionName")
    ExamSlotResponseDto toDto(ExamSlot entity);

    List<ExamSlotResponseDto> toDtoList(List<ExamSlot> entities);
}

