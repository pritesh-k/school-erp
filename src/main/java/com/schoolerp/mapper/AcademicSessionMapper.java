package com.schoolerp.mapper;

import com.schoolerp.entity.AcademicSession;
import com.schoolerp.newdtos.academic.AcademicSessionCreateDto;
import com.schoolerp.newdtos.academic.AcademicSessionResponseDto;
import com.schoolerp.newdtos.academic.AcademicSessionUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AcademicSessionMapper {
    AcademicSessionResponseDto toDto(AcademicSession entity);
    List<AcademicSessionResponseDto> toDto(List<AcademicSession> entities);

    AcademicSession toEntity(AcademicSessionCreateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AcademicSessionUpdateDto dto, @MappingTarget AcademicSession entity);
}

