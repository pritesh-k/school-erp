package com.schoolerp.mapper;

import com.schoolerp.dto.request.FeeStructureRequest;
import com.schoolerp.dto.response.FeeStructureResponse;
import com.schoolerp.entity.AcademicSession;
import com.schoolerp.entity.FeeStructure;
import com.schoolerp.entity.SchoolClass;
import com.schoolerp.enums.ClassStandard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FeeStructureItemMapper.class, AcademicSessionMapper.class, ClassMapper.class})
public interface FeeStructureMapper {
    FeeStructureResponse toResponse(FeeStructure feeStructure);
}

