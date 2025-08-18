package com.schoolerp.mapper;
import com.schoolerp.dto.response.TeacherDetailsResponseDto;
import com.schoolerp.dto.response.TeacherResponseDto;
import com.schoolerp.dto.response.TeachersAssignedDto;
import com.schoolerp.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AssignmentMapper.class})
public interface TeacherMapper {
    TeacherResponseDto toDto(Teacher entity);
    TeachersAssignedDto toAssignedDto(Teacher entity);
}