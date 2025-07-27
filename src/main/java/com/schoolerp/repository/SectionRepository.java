package com.schoolerp.repository;

import com.schoolerp.entity.Section;
import com.schoolerp.enums.SectionName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    Page<Section> findBySchoolClassId(Long classId, Pageable pageable);

    boolean existsByNameAndSchoolClassId(SectionName name, Long classId);

    boolean existsBySchoolClassIdAndName(Long classId, SectionName name);
}