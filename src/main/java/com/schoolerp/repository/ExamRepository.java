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
    Page<Exam> findByAcademicSession_Id(Long academicSessionId, Pageable pageable);
}