package com.schoolerp.repository;

import com.schoolerp.entity.ClassTeacherAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassTeacherAssignmentRepository extends JpaRepository<ClassTeacherAssignment, Long> {

    Page<ClassTeacherAssignment> findByTeacher_IdAndAcademicSession_Id(Pageable pageable, Long teacherId, Long id);

    Page<ClassTeacherAssignment> findByAcademicSession_Id(Pageable pageable, Long id);

    Optional<ClassTeacherAssignment> findBySection_IdAndAcademicSession_Id(Long sectionId, Long id);

    boolean existsBySection_IdAndAcademicSession_Id(Long id, Long id1);
}

