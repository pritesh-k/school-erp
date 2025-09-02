//package com.schoolerp.mapper;
//
//import com.schoolerp.dto.request.StudentFeeAssignmentRequest;
//import com.schoolerp.dto.response.StudentFeeAssignmentResponse;
//import com.schoolerp.entity.*;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;
//import org.mapstruct.Named;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring", uses = {StudentEnrollmentMapper.class, FeeStructureMapper.class})
//public interface StudentFeeAssignmentMapper {
//
//    // Entity to Response DTO
//    @Mapping(source = "studentEnrollment", target = "studentEnrollmentId", qualifiedByName = "enrollmentToId")
//    @Mapping(source = "studentEnrollment.student", target = "studentName", qualifiedByName = "studentToName")
//    @Mapping(source = "feeStructure", target = "feeStructureId", qualifiedByName = "feeStructureToId")
//    @Mapping(source = "feeStructure", target = "feeStructureName", qualifiedByName = "feeStructureToName")
//    StudentFeeAssignmentResponse toResponse(StudentFeeAssignment assignment);
//
//    // List mappings
//    List<StudentFeeAssignmentResponse> toResponseList(List<StudentFeeAssignment> assignments);
//
//    // Custom mapping methods
//    @Named("enrollmentToId")
//    default Long enrollmentToId(StudentEnrollment enrollment) {
//        return enrollment != null ? enrollment.getId() : null;
//    }
//
//    @Named("studentToName")
//    default String studentToName(Student student) {
//        if (student == null) return null;
//        return student.getFirstName() + " " + student.getLastName();
//    }
//
//    @Named("feeStructureToId")
//    default Long feeStructureToId(FeeStructure feeStructure) {
//        return feeStructure != null ? feeStructure.getId() : null;
//    }
//
//    @Named("feeStructureToName")
//    default String feeStructureToName(FeeStructure feeStructure) {
//        return feeStructure != null ? feeStructure.getName() : null;
//    }
//
//    @Named("idToEnrollment")
//    default StudentEnrollment idToEnrollment(Long enrollmentId) {
//        if (enrollmentId == null) return null;
//        StudentEnrollment enrollment = new StudentEnrollment();
//        enrollment.setId(enrollmentId);
//        return enrollment;
//    }
//
//    @Named("idToFeeStructure")
//    default FeeStructure idToFeeStructure(Long feeStructureId) {
//        if (feeStructureId == null) return null;
//        FeeStructure feeStructure = new FeeStructure();
//        feeStructure.setId(feeStructureId);
//        return feeStructure;
//    }
//}
