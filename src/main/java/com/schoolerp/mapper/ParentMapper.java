package com.schoolerp.mapper;

import com.schoolerp.dto.response.ParentResponseDto;
import com.schoolerp.entity.Parent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParentMapper {
    ParentResponseDto toDto(Parent parent);
}