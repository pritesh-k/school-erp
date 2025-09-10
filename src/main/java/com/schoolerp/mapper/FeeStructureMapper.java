package com.schoolerp.mapper;

import com.schoolerp.dto.response.FeeStructureResponse;
import com.schoolerp.entity.FeeStructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {FeeStructureItemMapper.class, ClassMapper.class})
public interface FeeStructureMapper {
    FeeStructureResponse toResponse(FeeStructure feeStructure);
}

