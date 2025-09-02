package com.schoolerp.repository;

import com.schoolerp.entity.ExamSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamSlotRepository extends JpaRepository<ExamSlot, Long> {
    Page<ExamSlot> findByExam_Id(Long examId, Pageable pageable);
}
