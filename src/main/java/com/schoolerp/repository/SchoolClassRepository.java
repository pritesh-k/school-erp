package com.schoolerp.repository;

import com.schoolerp.entity.SchoolClass;
import com.schoolerp.enums.ClassStandard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    Optional<SchoolClass> findByName(ClassStandard name);
    Page<SchoolClass> findByNameContainingIgnoreCase(String name, Pageable pageable);

    boolean existsByNameAndIdNot(ClassStandard name, Long excludeId);
}