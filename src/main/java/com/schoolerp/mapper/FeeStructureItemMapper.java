package com.schoolerp.mapper;

import com.schoolerp.dto.response.FeeStructureItemResponse;
import com.schoolerp.entity.FeeStructureItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {FeeHeadMapper.class})
public interface FeeStructureItemMapper {
    FeeStructureItemResponse toResponse(FeeStructureItem item);
}
