package com.schoolerp.repository;

import com.schoolerp.entity.TeacherSubjectAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherSubjectAssignmentRepository extends JpaRepository<TeacherSubjectAssignment, Long> {
    Page<TeacherSubjectAssignment> findByTeacher_Id(Long teacherId, Pageable pageable);
    List<TeacherSubjectAssignment> findBySectionSubjectAssignment_Section_Id(Long sectionId);
    Optional<TeacherSubjectAssignment> findBySectionSubjectAssignment_IdAndTeacher_Id(Long sectionSubjectAssignmentId, Long teacherId);

    Page<TeacherSubjectAssignment> findBySectionSubjectAssignment_Id(Long sectionSubjectAssignmentId, Pageable pageable);
}
