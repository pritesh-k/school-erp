package com.schoolerp.mapper;

import com.schoolerp.dto.response.FeeHeadResponse;
import com.schoolerp.entity.FeeHead;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeeHeadMapper {

    FeeHeadResponse toResponse(FeeHead feeHead);
}
