package com.schoolerp.repository;

import com.schoolerp.entity.SubjectAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectAssignmentRepository extends JpaRepository<SubjectAssignment, Long> {
    Optional<SubjectAssignment> findByTeacherIdAndSubjectIdAndSectionId(Long teacherId, Long subjectId, Long sectionId);
    Page<SubjectAssignment> findBySection_Id(Long sectionId, Pageable pageable);
    Page<SubjectAssignment> findBySubject_Id(Long subjectId, Pageable pageable);
    Page<SubjectAssignment> findByTeacher_Id(Long teacherId, Pageable pageable);
    Optional<SubjectAssignment> findBySubject_IdAndSection_Id(Long subjectId, Long sectionId);
}


