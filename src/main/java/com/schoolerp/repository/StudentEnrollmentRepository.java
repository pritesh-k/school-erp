package com.schoolerp.repository;

import com.schoolerp.entity.StudentEnrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long>, JpaSpecificationExecutor<StudentEnrollment> {

    Page<StudentEnrollment> findAllByAcademicSession_Id(Long sessionId, Pageable pageable);

    Page<StudentEnrollment> findAllBySchoolClass_IdAndAcademicSession_Id(Long classId, Long sessionId, Pageable pageable);

    List<StudentEnrollment> findByStudent_IdOrderByAcademicSession_StartDateDesc(Long studentId);

    Optional<StudentEnrollment> findByStudent_IdAndAcademicSession_Id(Long studentId, Long sessionId);

    Optional<StudentEnrollment> findByStudent_IdAndAcademicSession_Name
            (Long studentId, String sessionName);


    boolean existsByStudent_IdAndAcademicSession_Id(Long studentId, Long academicSessionId);

    boolean existsByStudent_IdAndAcademicSession_Name(Long studentId, String academicSessionName);


    Optional<StudentEnrollment> findByStudent_Id(Long studentId);

    List<StudentEnrollment> findBySchoolClass_IdAndSection_IdAndAcademicSession_NameAndActiveTrue(Long classId, Long sectionId, String academicSessionName);
}

