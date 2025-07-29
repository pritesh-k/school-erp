package com.schoolerp.mapper;

import com.schoolerp.dto.request.SectionSubjectAssignmentCreateDto;
import com.schoolerp.dto.request.TeacherSubjectAssignmentCreateDto;
import com.schoolerp.dto.response.SectionSubjectAssignmentResponseDto;
import com.schoolerp.dto.response.TeacherSubjectAssignmentResponseDto;
import com.schoolerp.entity.SectionSubjectAssignment;
import com.schoolerp.entity.TeacherSubjectAssignment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TeacherMapper.class})
public interface AssignmentMapper {
    SectionSubjectAssignment toEntity(SectionSubjectAssignmentCreateDto dto);
    SectionSubjectAssignmentResponseDto toDto(SectionSubjectAssignment entity);

    TeacherSubjectAssignment toEntity(TeacherSubjectAssignmentCreateDto dto);
    TeacherSubjectAssignmentResponseDto toDto(TeacherSubjectAssignment entity);
}
