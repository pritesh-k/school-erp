package com.schoolerp.mapper;

import com.schoolerp.dto.response.FeeRecordResponseDto;
import com.schoolerp.entity.FeeRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeeRecordMapper {
    FeeRecordResponseDto toDto(FeeRecord entity);
}