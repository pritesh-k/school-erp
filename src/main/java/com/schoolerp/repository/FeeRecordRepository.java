package com.schoolerp.repository;

import com.schoolerp.entity.FeeRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Repository
public interface FeeRecordRepository extends JpaRepository<FeeRecord, Long> {
    List<FeeRecord> findByStudentIdOrderByDueDateDesc(Long studentId);

    Page<FeeRecord> findByStudentId(Long studentId, Pageable pageable);

    // Total paid by student
    @Query("SELECT SUM(f.amount) FROM FeeRecord f WHERE f.student.id = :studentId AND f.status = 'PAID'")
    BigDecimal totalPaidByStudent(@Param("studentId") Long studentId);

    // Overdue unpaid fees
    @Query("SELECT f FROM FeeRecord f WHERE f.status = 'UNPAID' AND f.dueDate < CURRENT_DATE")
    List<FeeRecord> findOverdueFees();

    boolean existsByStudentId(Long studentId);
}