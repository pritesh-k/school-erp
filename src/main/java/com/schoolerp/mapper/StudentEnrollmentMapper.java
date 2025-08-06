package com.schoolerp.mapper;

import com.schoolerp.dto.request.StudentEnrollmentDTO;
import com.schoolerp.entity.StudentEnrollment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentEnrollmentMapper {
    StudentEnrollmentDTO toDto(StudentEnrollment entity);
    StudentEnrollment toEntity(StudentEnrollmentDTO dto);
    List<StudentEnrollmentDTO> toDtoList(List<StudentEnrollment> entities);
    List<StudentEnrollment> toEntityList(List<StudentEnrollmentDTO> dtos);
}

