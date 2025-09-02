//package com.schoolerp.mapper;
//
//import com.schoolerp.dto.request.FeeStructureRequest;
//import com.schoolerp.dto.response.FeeStructureResponse;
//import com.schoolerp.entity.AcademicSession;
//import com.schoolerp.entity.FeeStructure;
//import com.schoolerp.entity.SchoolClass;
//import com.schoolerp.enums.ClassStandard;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Named;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring", uses = {FeeStructureItemMapper.class, AcademicSessionMapper.class, ClassMapper.class})
//public interface FeeStructureMapper {
//
//    // Entity to Response DTO
//    @Mapping(source = "session", target = "sessionId", qualifiedByName = "sessionToId")
//    @Mapping(source = "session", target = "sessionName", qualifiedByName = "sessionToName")
//    @Mapping(source = "schoolClass", target = "classId", qualifiedByName = "classToId")
//    @Mapping(source = "schoolClass", target = "className", qualifiedByName = "classToName")
//    FeeStructureResponse toResponse(FeeStructure feeStructure);
//
//    // List mappings
//    List<FeeStructureResponse> toResponseList(List<FeeStructure> feeStructures);
//
//    // Custom mapping methods
//    @Named("sessionToId")
//    default Long sessionToId(AcademicSession session) {
//        return session != null ? session.getId() : null;
//    }
//
//    @Named("sessionToName")
//    default String sessionToName(AcademicSession session) {
//        return session != null ? session.getName() : null;
//    }
//
//    @Named("classToId")
//    default Long classToId(SchoolClass schoolClass) {
//        return schoolClass != null ? schoolClass.getId() : null;
//    }
//
//    @Named("classToName")
//    default ClassStandard classToName(SchoolClass schoolClass) {
//        return schoolClass != null ? schoolClass.getName() : null;
//    }
//
//    @Named("idToSession")
//    default AcademicSession idToSession(Long sessionId) {
//        if (sessionId == null) return null;
//        AcademicSession academicSession = new AcademicSession();
//        academicSession.setId(sessionId);
//        return academicSession;
//    }
//
//    @Named("idToClass")
//    default SchoolClass idToClass(Long classId) {
//        if (classId == null) return null;
//        SchoolClass schoolClass = new SchoolClass();
//        schoolClass.setId(classId);
//        return schoolClass;
//    }
//}
//
