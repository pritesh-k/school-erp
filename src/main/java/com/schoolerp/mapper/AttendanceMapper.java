package com.schoolerp.mapper;

import com.schoolerp.dto.response.AttendanceResponseDto;
import com.schoolerp.entity.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TeacherMapper.class, StudentMapper.class, SectionMapper.class})
public interface AttendanceMapper {

    @Mapping(target = "student", source = "studentEnrollment.student")
    @Mapping(target = "section", source = "studentEnrollment.section")
    @Mapping(target = "recordedBy", source = "recordedBy")
    AttendanceResponseDto toDto(Attendance attendance);
}