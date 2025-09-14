package com.schoolerp.repository;

import com.schoolerp.entity.StudentEnrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long>, JpaSpecificationExecutor<StudentEnrollment> {

    Page<StudentEnrollment> findAllByAcademicSession_Id(Long sessionId, Pageable pageable);

    Optional<StudentEnrollment> findByStudent_IdAndAcademicSession_Name
            (Long studentId, String sessionName);


    boolean existsByStudent_IdAndAcademicSession_Name(Long studentId, String academicSessionName);


    Optional<StudentEnrollment> findByStudent_Id(Long studentId);

    List<StudentEnrollment> findBySchoolClass_IdAndSection_IdAndAcademicSession_NameAndActiveTrue(Long classId, Long sectionId, String academicSessionName);

    List<StudentEnrollment> findBySchoolClass_IdAndAcademicSession_Id(Long id, Long id1);

    @Query("""
        SELECT COUNT(se)
        FROM StudentEnrollment se
        WHERE se.academicSession.id = :sessionId
          AND se.active = true
    """)
    long countTotalStudents(@Param("sessionId") Long sessionId);

    @Query("""
        SELECT COUNT(se)
        FROM StudentEnrollment se
        WHERE se.academicSession.id = :sessionId
          AND se.schoolClass.id = :classId
          AND se.section.id = :sectionId
          AND se.active = true
    """)
    long countTotalStudentsByClassAndSection(@Param("sessionId") Long sessionId,
                                             @Param("classId") Long classId,
                                             @Param("sectionId") Long sectionId);

}

