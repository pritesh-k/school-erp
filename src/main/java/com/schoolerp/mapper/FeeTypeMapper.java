package com.schoolerp.mapper;

import com.schoolerp.dto.response.FeeTypeResponseDto;
import com.schoolerp.entity.FeeType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeeTypeMapper {
    FeeTypeResponseDto toDto(FeeType feeType);
}