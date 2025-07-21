package com.schoolerp.mapper;

import com.schoolerp.dto.request.SectionSummaryDto;
import com.schoolerp.dto.response.SectionResponseDto;
import com.schoolerp.entity.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    SectionResponseDto toDto(Section entity);

    SectionSummaryDto toSectionSummary(Section section);
    List<SectionSummaryDto> toSectionSummaryDtos(List<Section> sections);

}