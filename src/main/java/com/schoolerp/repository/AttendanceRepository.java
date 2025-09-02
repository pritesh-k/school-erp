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
}