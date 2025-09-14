package com.schoolerp.repository;

import com.schoolerp.dto.response.AttendanceSummaryDto;
import com.schoolerp.dto.response.attendance.ClassAttendanceSummaryDto;
import com.schoolerp.dto.response.attendance.SectionAttendanceSummaryDto;
import com.schoolerp.entity.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    @Query("SELECT a.status, COUNT(a) FROM Attendance a WHERE a.date = :date GROUP BY a.status")
    List<Object[]> countByStatusForDate(@Param("date") LocalDate date);

    @Query("SELECT se.schoolClass.id, se.schoolClass.name, se.section.name, a.status, COUNT(a) " +
            "FROM Attendance a JOIN a.studentEnrollment se " +
            "WHERE a.date = :date " +
            "GROUP BY se.schoolClass.id, se.schoolClass.name, se.section.name, a.status")
    List<Object[]> countByClassAndStatusForDate(@Param("date") LocalDate date);

    @Query("SELECT COUNT(se) FROM StudentEnrollment se WHERE se.active = true")
    long countActiveStudents();

    @Query("SELECT COUNT(se) FROM StudentEnrollment se WHERE se.active = true AND se.schoolClass.id = :classId")
    long countActiveStudentsInClass(@Param("classId") Long classId);

    @Query("""
    SELECT
        sc.id,
        sc.name,
        null,
        null,
        COUNT(DISTINCT se.student.id),
        SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END),
        SUM(CASE WHEN a.status = 'ABSENT' THEN 1 ELSE 0 END),
        SUM(CASE WHEN a.status = 'LATE' THEN 1 ELSE 0 END),
        SUM(CASE WHEN a.status = 'LEAVE' THEN 1 ELSE 0 END),
        CASE WHEN COUNT(a) > 0 THEN (SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) * 100.0) / COUNT(a) ELSE 0.0 END
    FROM StudentEnrollment se
    LEFT JOIN Attendance a ON a.studentEnrollment = se AND (:date IS NULL OR a.date = :date)
    JOIN se.schoolClass sc
    WHERE se.academicSession.name = :academicSessionName AND se.active = true
    GROUP BY sc.id, sc.name
    ORDER BY sc.name ASC
    """)
    Page<Object[]> getClassWiseSummaryRaw(
            @Param("academicSessionName") String academicSessionName,
            @Param("date") LocalDate date,
            Pageable pageable);


    // Class drill-down: Section-wise summary
    @Query("""
        SELECT
            :date,
            sc.id,
            sc.name,
            s.id,
            s.name,
            COUNT(DISTINCT se.student.id),
            SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END),
            SUM(CASE WHEN a.status = 'ABSENT' THEN 1 ELSE 0 END),
            SUM(CASE WHEN a.status = 'LATE' THEN 1 ELSE 0 END),
            SUM(CASE WHEN a.status = 'LEAVE' THEN 1 ELSE 0 END),
            CASE WHEN COUNT(a) > 0 THEN (SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) * 100.0) / COUNT(a) ELSE 0.0 END
        FROM StudentEnrollment se
        LEFT JOIN Attendance a ON a.studentEnrollment = se AND (:date IS NULL OR a.date = :date)
        JOIN se.section s
        JOIN se.schoolClass sc
        WHERE se.academicSession.name = :academicSessionName
        AND sc.id = :classId
        AND se.active = true
        GROUP BY sc.id, sc.name, s.id, s.name
        """)
    Page<Object[]> getSectionWiseSummaryRaw(
            @Param("academicSessionName") String academicSessionName,
            @Param("classId") Long classId,
            @Param("date") LocalDate date,
            Pageable pageable);

    // Section detail: Daily attendance details
    @Query("""
        SELECT
            a.date,
            sc.id,
            sc.name,
            s.id,
            s.name,
            COUNT(DISTINCT se.student.id),
            SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END),
            SUM(CASE WHEN a.status = 'ABSENT' THEN 1 ELSE 0 END),
            SUM(CASE WHEN a.status = 'LATE' THEN 1 ELSE 0 END),
            SUM(CASE WHEN a.status = 'LEAVE' THEN 1 ELSE 0 END),
            CASE WHEN COUNT(a) > 0 THEN (SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) * 100.0) / COUNT(a) ELSE 0.0 END
        FROM StudentEnrollment se
        JOIN Attendance a ON a.studentEnrollment = se
        JOIN se.section s 
        JOIN se.schoolClass sc
        WHERE se.academicSession.name = :academicSessionName 
        AND sc.id = :classId 
        AND s.id = :sectionId 
        AND se.active = true
        AND (:date IS NULL OR a.date = :date)
        GROUP BY a.date, sc.id, sc.name, s.id, s.name
        """)
    Page<Object[]> getDetailedSummaryRaw(
            @Param("academicSessionName") String academicSessionName,
            @Param("classId") Long classId,
            @Param("sectionId") Long sectionId,
            @Param("date") LocalDate date,
            Pageable pageable);

    // Percentage of students present today
    @Query("""
    SELECT
        CASE WHEN COUNT(DISTINCT se.student.id) > 0 
             THEN (COUNT(DISTINCT CASE WHEN a.status = 'PRESENT' THEN se.student.id END) * 100.0) / COUNT(DISTINCT se.student.id)
             ELSE 0.0 
        END
    FROM StudentEnrollment se
    LEFT JOIN Attendance a ON a.studentEnrollment = se AND a.date = CURRENT_DATE
    WHERE se.academicSession.name = :academicSessionName 
    AND se.active = true
    """)
    Double getTodayAttendancePercentage(@Param("academicSessionName") String academicSessionName);

}