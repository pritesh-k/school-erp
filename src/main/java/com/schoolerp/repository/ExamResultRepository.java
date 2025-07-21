package com.schoolerp.repository;

import com.schoolerp.entity.ExamResult;
import com.schoolerp.enums.ResultStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    // Get results by student
    Page<ExamResult> findByStudentId(Long studentId, Pageable pageable);

    // Get results by exam
    Page<ExamResult> findByExamId(Long examId, Pageable pageable);

    // Get results by student and exam
    Page<ExamResult> findByStudentIdAndExamId(Long studentId, Long examId, Pageable pageable);

    // Get specific result by student, exam, and subject
    Optional<ExamResult> findByStudentIdAndExamIdAndSubjectId(Long studentId, Long examId, Long subjectId);

    // Get results by exam and class
    @Query("""
    SELECT er FROM ExamResult er
    JOIN er.student s
    WHERE er.exam.id = :examId AND s.schoolClass.id = :classId
    """)
    Page<ExamResult> findByExamIdAndClassId(@Param("examId") Long examId,
                                            @Param("classId") Long classId,
                                            Pageable pageable);


    // Average score for a student in an exam
    @Query("""
        SELECT AVG(er.score) FROM ExamResult er
        WHERE er.student.id = :studentId AND er.exam.id = :examId
    """)
    BigDecimal findAverageScoreByStudentInExam(@Param("studentId") Long studentId, @Param("examId") Long examId);

    // Average score per subject in an exam
    @Query("""
    SELECT er.student.id, AVG(er.score)
    FROM ExamResult er
    WHERE er.exam.id = :examId
    GROUP BY er.student.id
    """)
    List<Object[]> getAverageScoresByExam(@Param("examId") Long examId);

    @Query("""
    SELECT er.student.id, SUM(er.score) AS total
    FROM ExamResult er
    WHERE er.exam.id = :examId
    GROUP BY er.student.id
    ORDER BY total DESC
    """)
    Page<Object[]> getTopStudentsByExam(@Param("examId") Long examId, Pageable pageable);

    @Query("""
    SELECT COUNT(er) FROM ExamResult er
    WHERE er.exam.id = :examId AND er.status = :status
""")
    Long countByExamIdAndStatus(@Param("examId") Long examId, @Param("status") String status);

    boolean existsByStudentId(Long studentId);
}