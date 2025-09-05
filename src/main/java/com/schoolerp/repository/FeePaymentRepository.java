package com.schoolerp.repository;

import com.schoolerp.entity.FeePayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {
    @Query("SELECT p FROM FeePayment p " +
            "WHERE p.studentFeeAssignment.studentEnrollment.student.id = :studentId")
    Page<FeePayment> findByStudentId(@Param("studentId") Long studentId, Pageable pageable);

    @Query("SELECT p FROM FeePayment p " +
            "WHERE p.studentFeeAssignment.studentEnrollment.student.id = :studentId " +
            "AND p.studentFeeAssignment.studentEnrollment.academicSession.id = :sessionId")
    Page<FeePayment> findByStudentIdAndSessionId(@Param("studentId") Long studentId,
                                                 @Param("sessionId") Long sessionId,
                                                 Pageable pageable);
}

