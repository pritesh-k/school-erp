package com.schoolerp.mapper;

import com.schoolerp.dto.response.TeacherDetailsResponseDto;
import com.schoolerp.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TeacherMapper.class, SubjectMapper.class, SectionMapper.class})
public interface TeacherDetailsMapper {
    TeacherDetailsResponseDto toDto(Teacher entity);
}
