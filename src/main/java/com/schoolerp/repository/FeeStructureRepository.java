package com.schoolerp.repository;

import com.schoolerp.entity.FeeStructure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeeStructureRepository extends JpaRepository<FeeStructure, Long> {
    boolean existsByAcademicSession_IdAndSchoolClass_Id(Long sessionId, Long classId);

    Page<FeeStructure> findByAcademicSession_Id(Pageable pageable, Long id);

    Optional<FeeStructure> findBySchoolClass_Id(Long id);
}