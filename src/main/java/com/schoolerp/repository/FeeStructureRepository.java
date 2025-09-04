package com.schoolerp.repository;

import com.schoolerp.entity.FeeStructure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeeStructureRepository extends JpaRepository<FeeStructure, Long>, JpaSpecificationExecutor<FeeStructure> {
    // New methods for filtering by sessionId and classId
    Page<FeeStructure> findBySessionIdAndSchoolClassIdOrderByNameAsc( Pageable pageable,
            Long sessionId, Long schoolClassId);
}