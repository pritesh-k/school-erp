package com.schoolerp.mapper;

import com.schoolerp.dto.request.ExamInvigilationDutyCreateDto;
import com.schoolerp.dto.response.ExamInvigilationDutyResponseDto;
import com.schoolerp.entity.ExamInvigilationDuty;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamInvigilationDutyMapper {
    ExamInvigilationDuty toEntity(ExamInvigilationDutyCreateDto dto);
    ExamInvigilationDutyResponseDto toDto(ExamInvigilationDuty entity);
    List<ExamInvigilationDutyResponseDto> toDtoList(List<ExamInvigilationDuty> entities);
}

