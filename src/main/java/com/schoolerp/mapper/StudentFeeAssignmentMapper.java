package com.schoolerp.mapper;

import com.schoolerp.dto.response.StudentFeeAssignmentResponse;
import com.schoolerp.entity.StudentFeeAssignment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StudentEnrollmentMapper.class, FeeStructureMapper.class})
public interface StudentFeeAssignmentMapper {
    StudentFeeAssignmentResponse toResponse(StudentFeeAssignment assignment);
}
