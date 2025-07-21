package com.schoolerp.mapper;

import com.schoolerp.dto.response.DocumentResponseDto;
import com.schoolerp.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    DocumentResponseDto toDto(Document entity);
}