package com.schoolerp.mapper;

import com.schoolerp.dto.response.StudentDetailedResponseDto;
import com.schoolerp.dto.response.StudentResponseDto;
import com.schoolerp.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ParentMapper.class})
public interface StudentMapper {
    StudentResponseDto toDto(Student entity);
    @Mapping(target = "student", source = "entity") // maps the whole Student to StudentResponseDto
    @Mapping(target = "parent", source = "entity.parent") // maps Student.parent to ParentResponseDto
    StudentDetailedResponseDto toDetailedDto(Student entity);
}