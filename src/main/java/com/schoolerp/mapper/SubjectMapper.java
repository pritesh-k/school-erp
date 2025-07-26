package com.schoolerp.mapper;

import com.schoolerp.dto.request.SubjectSummaryDto;
import com.schoolerp.dto.response.SubjectResponseDto;
import com.schoolerp.entity.SchoolClass;
import com.schoolerp.entity.Subject;
import com.schoolerp.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    SubjectResponseDto toDto(Subject entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "assignedClasses", source = "classes", qualifiedByName = "classNames")
    SubjectSummaryDto toSubjectSummary(Subject subject);
    @Named("classNames")
    static List<String> mapClassNames(Set<SchoolClass> classes) {
        if (classes == null) return null;
        return classes.stream()
                .map(schoolClass -> schoolClass.getName().name()) // Convert Enum to String
                .collect(Collectors.toList());
    }
}