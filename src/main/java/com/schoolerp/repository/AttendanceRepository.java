package com.schoolerp.repository;

import com.schoolerp.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {


    boolean existsByStudentEnrollment_IdAndDate(Long studentEnrollmentId, LocalDate date);
    @Query("""
        SELECT a.status, COUNT(a) 
        FROM Attendance a
        WHERE a.studentEnrollment.academicSession.id = :academicSessionId
        GROUP BY a.status
    """)
    List<Object[]> countByStatusForSession(@Param("academicSessionId") Long academicSessionId);
}