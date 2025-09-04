package com.schoolerp.mapper;

import com.schoolerp.dto.request.StudentFeeAssignmentRequest;
import com.schoolerp.dto.response.StudentFeeAssignmentResponse;
import com.schoolerp.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StudentEnrollmentMapper.class, FeeStructureMapper.class})
public interface StudentFeeAssignmentMapper {
    StudentFeeAssignmentResponse toResponse(StudentFeeAssignment assignment);
}
