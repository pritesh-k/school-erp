//package com.schoolerp.repository;
//
//import com.schoolerp.entity.FeeStructure;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface FeeStructureRepository extends JpaRepository<FeeStructure, Long>, JpaSpecificationExecutor<FeeStructure> {
//    // New methods for filtering by sessionId and classId
//    Page<FeeStructure> findByIsActiveTrueAndSessionIdAndSchoolClassIdOrderByNameAsc( Pageable pageable,
//            Long sessionId, Long schoolClassId);
//
//    List<FeeStructure> findBySessionIdAndIsActiveTrue(Long sessionId);
//
//    Optional<FeeStructure> findBySessionIdAndSchoolClassIdAndIsActiveTrue(Long sessionId, Long classId);
//
//    @Query("SELECT fs FROM FeeStructure fs " +
//            "JOIN FETCH fs.session s " +
//            "JOIN FETCH fs.schoolClass sc " +
//            "WHERE fs.isActive = true")
//    List<FeeStructure> findAllActiveWithDetails();
//}