package com.schoolerp.mapper;
import com.schoolerp.dto.response.TeacherResponseDto;
import com.schoolerp.dto.response.TeachersAssignedDto;
import com.schoolerp.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherResponseDto toDto(Teacher entity);
    TeachersAssignedDto toAssignedDto(Teacher entity);
}