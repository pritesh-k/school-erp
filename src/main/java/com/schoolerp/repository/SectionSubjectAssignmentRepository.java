package com.schoolerp.repository;

import com.schoolerp.entity.SectionSubjectAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionSubjectAssignmentRepository extends JpaRepository<SectionSubjectAssignment, Long> {
    Page<SectionSubjectAssignment> findBySubjectId(Long subjectId, Pageable pageable);

    Page<SectionSubjectAssignment> findBySectionId(Long sectionId, Pageable pageable);

    Optional<SectionSubjectAssignment> findBySection_IdAndSubject_Id(Long sectionId, Long subjectId);
}
