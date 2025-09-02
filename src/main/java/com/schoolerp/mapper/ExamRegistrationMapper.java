package com.schoolerp.mapper;

import com.schoolerp.dto.request.ExamRegistrationCreateDto;
import com.schoolerp.dto.response.ExamRegistrationResponseDto;
import com.schoolerp.entity.ExamRegistration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamRegistrationMapper {
    ExamRegistration toEntity(ExamRegistrationCreateDto dto);
    ExamRegistrationResponseDto toDto(ExamRegistration entity);
    List<ExamRegistrationResponseDto> toDtoList(List<ExamRegistration> entities);
}

