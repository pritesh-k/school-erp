package com.schoolerp.mapper;

import com.schoolerp.dto.request.feeHead.FeeHeadRequest;
import com.schoolerp.entity.FeeHead;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeeHeadMapper {
    FeeHeadRequest toDto(FeeHead entity);
}
