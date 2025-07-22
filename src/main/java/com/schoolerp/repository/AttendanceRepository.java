package com.schoolerp.repository;

import com.schoolerp.entity.Attendance;
import com.schoolerp.entity.Teacher;
import com.schoolerp.enums.AttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {

    Page<Attendance> findByStudentIdOrderByDateDesc(Long studentId, Pageable pageable);

    Page<Attendance> findBySectionIdAndDate(Long sectionId, LocalDate date, Pageable pageable);

    List<Attendance> findBySectionIdAndDate(Long sectionId, LocalDate date);

    Page<Attendance> findByStatus(AttendanceStatus status, Pageable pageable);

    Page<Attendance> findByStatusAndStudentId(AttendanceStatus status, Long studentId, Pageable pageable);

    Page<Attendance> findByStatusOrderByDateDesc(AttendanceStatus status, Pageable pageable);

    // Check if attendance exists for a student on a specific date
    Optional<Attendance> findByStudentIdAndDate(Long studentId, LocalDate date);

    // Find attendance by date range
    List<Attendance> findByStudentIdAndDateBetween(Long studentId, LocalDate startDate, LocalDate endDate);

    List<Attendance> findBySectionIdAndDateBetween(Long sectionId, LocalDate startDate, LocalDate endDate);

    @Query("""
    SELECT
      COUNT(a) AS total,
      SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) AS present,
      SUM(CASE WHEN a.status = 'ABSENT' THEN 1 ELSE 0 END) AS absent
    FROM Attendance a
    WHERE a.student.id = :studentId
""")
    Object[] getAttendanceStatsByStudent(@Param("studentId") Long studentId);

    @Query("""
    SELECT
      COUNT(a) AS total,
      SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) AS present,
      SUM(CASE WHEN a.status = 'ABSENT' THEN 1 ELSE 0 END) AS absent
    FROM Attendance a
    WHERE a.student.id = :studentId AND a.date BETWEEN :startDate AND :endDate
""")
    Object[] getAttendanceStatsByStudentAndDateRange(@Param("studentId") Long studentId,
                                                     @Param("startDate") LocalDate startDate,
                                                     @Param("endDate") LocalDate endDate);

    @Query("""
    SELECT a.student.id, COUNT(a),
           SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END),
           SUM(CASE WHEN a.status = 'ABSENT' THEN 1 ELSE 0 END)
    FROM Attendance a
    WHERE a.section.id = :sectionId
    GROUP BY a.student.id
""")
    List<Object[]> getClassAttendanceSummary(@Param("sectionId") Long sectionId);

    @Query("""
    SELECT a.student.id, COUNT(a),
           SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END),
           SUM(CASE WHEN a.status = 'ABSENT' THEN 1 ELSE 0 END)
    FROM Attendance a
    WHERE a.section.id = :sectionId AND a.date BETWEEN :startDate AND :endDate
    GROUP BY a.student.id
""")
    List<Object[]> getClassAttendanceSummaryByDateRange(@Param("sectionId") Long sectionId,
                                                        @Param("startDate") LocalDate startDate,
                                                        @Param("endDate") LocalDate endDate);

    // Count present/absent days
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.status = 'PRESENT'")
    Long countPresentDays(@Param("studentId") Long studentId);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.status = 'ABSENT'")
    Long countAbsentDays(@Param("studentId") Long studentId);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.status = 'PRESENT' AND a.date BETWEEN :startDate AND :endDate")
    Long countPresentDaysByDateRange(@Param("studentId") Long studentId,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.status = 'ABSENT' AND a.date BETWEEN :startDate AND :endDate")
    Long countAbsentDaysByDateRange(@Param("studentId") Long studentId,
                                    @Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate);

    // Daily stats
    @Query("""
        SELECT a.date, COUNT(a) FROM Attendance a
        WHERE a.section.id = :sectionId AND a.status = 'PRESENT'
        GROUP BY a.date
        ORDER BY a.date DESC
    """)
    List<Object[]> dailyAttendanceStats(@Param("sectionId") Long sectionId);

    @Query("""
        SELECT a.date, 
               COUNT(a) AS totalStudents,
               SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) AS presentCount,
               SUM(CASE WHEN a.status = 'ABSENT' THEN 1 ELSE 0 END) AS absentCount
        FROM Attendance a
        WHERE a.section.id = :sectionId AND a.date BETWEEN :startDate AND :endDate
        GROUP BY a.date
        ORDER BY a.date DESC
    """)
    List<Object[]> dailyAttendanceStatsByDateRange(@Param("sectionId") Long sectionId,
                                                   @Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);

    // Monthly attendance percentage
    @Query("""
        SELECT EXTRACT(MONTH FROM a.date) AS month,
               EXTRACT(YEAR FROM a.date) AS year,
               COUNT(a) AS totalDays,
               SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) AS presentDays,
               ROUND((SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) * 100.0) / COUNT(a), 2) AS attendancePercentage
        FROM Attendance a
        WHERE a.student.id = :studentId
        GROUP BY EXTRACT(MONTH FROM a.date), EXTRACT(YEAR FROM a.date)
        ORDER BY year DESC, month DESC
    """)
    List<Object[]> getMonthlyAttendancePercentage(@Param("studentId") Long studentId);

    Optional<Attendance> findByStudentIdAndSectionIdAndDate(Long studentId, Long sectionId, LocalDate date);
    boolean existsByStudentIdAndSectionIdAndDate(Long studentId, Long sectionId, LocalDate date);

    Page<Attendance> findBySectionId(Long sectionId, Pageable pageable);

    Page<Attendance> findByDate(LocalDate date, Pageable pageable);

    Page<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Modifying
    @Query("INSERT INTO Attendance (student, section, date, status, remarks, recordedBy) " +
            "SELECT s, s.section, :date, :status, :remarks, :teacher " +
            "FROM Student s WHERE s.section.id = :sectionId")
    int markAllStudentsWithStatus(@Param("sectionId") Long sectionId,
                                  @Param("date") LocalDate date,
                                  @Param("status") AttendanceStatus status,
                                  @Param("remarks") String remarks,
                                  @Param("teacher") Teacher teacher);

    Page<Attendance> findBySection_SchoolClass_Id(Long classId, Pageable pageable);

    List<Attendance> findBySection_SchoolClass_IdAndDate(Long classId, LocalDate date);

    List<Attendance> findBySection_SchoolClass_IdAndDateBetween(Long classId,
                                                                LocalDate start,
                                                                LocalDate end);

    List<Attendance> findByDateBetween(LocalDate start, LocalDate end);

    boolean existsByStudentId(Long studentId);

    Page<Attendance> findByDateAndSection_SchoolClass_Id(LocalDate today, Long classId, Pageable pageable);
}