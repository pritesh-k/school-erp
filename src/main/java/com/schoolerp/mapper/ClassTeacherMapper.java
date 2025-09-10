package com.schoolerp.mapper;

import com.schoolerp.dto.response.ClassTeacherAssignmentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TeacherMapper.class, SectionMapper.class})
public interface ClassTeacherMapper {
    ClassTeacherAssignmentResponse toDto(com.schoolerp.entity.ClassTeacherAssignment entity);
}
