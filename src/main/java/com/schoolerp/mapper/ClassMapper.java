package com.schoolerp.mapper;
import com.schoolerp.dto.response.ClassResponseDto;
import com.schoolerp.entity.SchoolClass;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassResponseDto toDto(SchoolClass entity);
}