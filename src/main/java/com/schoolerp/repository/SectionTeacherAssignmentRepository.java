package com.schoolerp.repository;

import com.schoolerp.entity.SectionTeacherAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionTeacherAssignmentRepository extends JpaRepository<SectionTeacherAssignment, Long> {
    List<SectionTeacherAssignment> findBySectionId(Long sectionId);
    List<SectionTeacherAssignment> findByTeacherId(Long teacherId);
    Optional<SectionTeacherAssignment> findByTeacherIdAndSectionId(Long teacherId, Long sectionId);
}
