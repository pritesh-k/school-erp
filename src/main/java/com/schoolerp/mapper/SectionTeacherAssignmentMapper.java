package com.schoolerp.mapper;

import com.schoolerp.dto.response.AssignmentResponse;
import com.schoolerp.entity.SectionTeacherAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { TeacherMapper.class, SectionMapper.class })
public interface SectionTeacherAssignmentMapper {

    AssignmentResponse toDto(SectionTeacherAssignment entity);
}
