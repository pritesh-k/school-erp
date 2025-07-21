package com.schoolerp.mapper;

import com.schoolerp.dto.response.StudentResponseDto;
import com.schoolerp.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentResponseDto toDto(Student entity);
}