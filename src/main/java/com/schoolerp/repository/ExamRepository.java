package com.schoolerp.repository;

import com.schoolerp.entity.Exam;
import com.schoolerp.enums.Term;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    Page<Exam> findBySchoolClassId(Long classId, Pageable pageable);
    Page<Exam> findByTerm(Term term, Pageable pageable);
    Page<Exam> findByName(String name, Pageable pageable);
    Page<Exam> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Exam> findByStartDateBetween(LocalDate start, LocalDate end, Pageable pageable);
    Page<Exam> findByEndDateAfter(LocalDate date, Pageable pageable); // upcoming exams
    Page<Exam> findByEndDateBefore(LocalDate date, Pageable pageable); // past exams
    Page<Exam> findBySchoolClassIdAndTerm(Long classId, Term term, Pageable pageable);
    Page<Exam> findBySchoolClassIdAndStartDateBetween(Long classId, LocalDate start, LocalDate end, Pageable pageable);

    // Exams by subject
    @Query("SELECT e FROM Exam e JOIN e.subjects s WHERE s.id = :subjectId")
    Page<Exam> findBySubjectId(@Param("subjectId") Long subjectId, Pageable pageable);

    @Query("SELECT e FROM Exam e WHERE e.schoolClass.id = :classId " +
            "AND ((e.startDate <= :endDate AND e.endDate >= :startDate))")
    List<Exam> findBySchoolClassIdAndDateRange(@Param("classId") Long schoolClassId,
                                               @Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);


    boolean existsBySchoolClassIdAndTermAndNameNot(Long schoolClassId, Term term, String name);
}