package com.schoolerp.mapper;

import com.schoolerp.dto.request.SubjectSummaryDto;
import com.schoolerp.dto.response.SubjectResponseDto;
import com.schoolerp.entity.Subject;
import com.schoolerp.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    SubjectResponseDto toDto(Subject entity);
    SubjectSummaryDto toSubjectSummary(Subject subject);
}