package com.schoolerp.mapper;

import com.schoolerp.dto.request.AssignmentRequest;
import com.schoolerp.dto.request.SectionTeacherAssignmentListDto;
import com.schoolerp.dto.response.AssignmentResponse;
import com.schoolerp.entity.SectionTeacherAssignment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { TeacherMapper.class, SectionMapper.class })
public interface SectionTeacherAssignmentMapper {

    SectionTeacherAssignmentListDto onlyData(SectionTeacherAssignment dto);
    AssignmentResponse toDto(SectionTeacherAssignment entity);
}
