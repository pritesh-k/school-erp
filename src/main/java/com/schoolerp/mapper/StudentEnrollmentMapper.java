package com.schoolerp.mapper;

import com.schoolerp.dto.request.StudentEnrollmentDTO;
import com.schoolerp.dto.response.enrollments.StudentEnrollmentResDto;
import com.schoolerp.entity.StudentEnrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClassMapper.class,
        SectionMapper.class, StudentMapper.class, AcademicSessionMapper.class})
public interface StudentEnrollmentMapper {
    StudentEnrollmentDTO toDto(StudentEnrollment entity);
    StudentEnrollment toEntity(StudentEnrollmentDTO dto);
    List<StudentEnrollmentDTO> toDtoList(List<StudentEnrollment> entities);
    List<StudentEnrollment> toEntityList(List<StudentEnrollmentDTO> dtos);

    @Mapping(target = "student", source = "entity.student")
    @Mapping(target = "section", source = "entity.section")
    @Mapping(target = "schoolClass", source = "entity.schoolClass")
    @Mapping(target = "academicSession", source = "entity.academicSession")
    StudentEnrollmentResDto toListEnroll(StudentEnrollment entity);
}

