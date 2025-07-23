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

    Page<FeeRecord> findByStudentFeeAssignment_Student_Id(Long studentId, Pageable pageable);

    boolean existsByStudentFeeAssignment_Student_Id(Long studentId);

    List<FeeRecord> findByStudentFeeAssignmentId(Long assignmentId);

}