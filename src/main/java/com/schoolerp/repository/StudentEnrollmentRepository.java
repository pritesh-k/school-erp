package com.schoolerp.repository;

import com.schoolerp.entity.StudentEnrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {

    Page<StudentEnrollment> findAllByAcademicSession_Id(Long sessionId, Pageable pageable);

    Page<StudentEnrollment> findAllBySchoolClass_IdAndAcademicSession_Id(Long classId, Long sessionId, Pageable pageable);

    List<StudentEnrollment> findByStudent_IdOrderByAcademicSession_StartDateDesc(Long studentId);

    Optional<StudentEnrollment> findByStudent_IdAndAcademicSession_Id(Long studentId, Long sessionId);

    boolean existsByStudent_IdAndAcademicSession_Id(Long studentId, Long academicSessionId);

    Optional<StudentEnrollment> findByStudent_Id(Long studentId);
}

