package com.schoolerp.mapper;

import com.schoolerp.dto.request.SectionSummaryDto;
import com.schoolerp.dto.response.SectionResponseDto;
import com.schoolerp.entity.SchoolClass;
import com.schoolerp.entity.Section;
import com.schoolerp.enums.SectionName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    SectionResponseDto toDto(Section entity);

    @Mapping(target = "sectionName", source = "name", qualifiedByName = "sectionNameToString")
    @Mapping(target = "className", source = "schoolClass", qualifiedByName = "schoolClassNameToString")
    SectionSummaryDto toSectionSummary(Section section);

    @Named("sectionNameToString")
    static String mapSectionName(SectionName name) {
        return name == null ? null : name.name();
    }

    @Named("schoolClassNameToString")
    static String mapSchoolClassName(SchoolClass schoolClass) {
        return (schoolClass == null || schoolClass.getName() == null) ? null : schoolClass.getName().name();
    }
    List<SectionSummaryDto> toSectionSummaryDtos(List<Section> sections);

}