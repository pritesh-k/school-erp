package com.schoolerp.repository;

import com.schoolerp.entity.SubjectAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectAssignmentRepository extends JpaRepository<SubjectAssignment, Long> {
    Optional<SubjectAssignment> findByTeacherIdAndSubjectIdAndSectionId(Long teacherId, Long subjectId, Long sectionId);
    List<SubjectAssignment> findBySectionId(Long sectionId);
    List<SubjectAssignment> findByTeacherId(Long teacherId);
}

