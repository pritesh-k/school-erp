package com.schoolerp.mapper;

import com.schoolerp.dto.response.SubjectAssignmentResponseDto;
import com.schoolerp.entity.SubjectAssignment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        TeacherMapper.class,
        SubjectMapper.class,
        SectionMapper.class
})
public interface SubjectAssignmentMapper {
    SubjectAssignmentResponseDto toDto(SubjectAssignment entity);
}
