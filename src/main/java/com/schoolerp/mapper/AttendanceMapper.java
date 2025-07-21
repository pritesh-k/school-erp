package com.schoolerp.mapper;

import com.schoolerp.dto.response.AttendanceResponseDto;
import com.schoolerp.entity.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {
    AttendanceResponseDto toDto(Attendance attendance);
}