//package com.schoolerp.repository;
//
//import com.schoolerp.entity.FeePayment;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Repository
//public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {
//
//    List<FeePayment> findByStudentFeeAssignmentIdOrderByPaymentDateDesc(Long assignmentId);
//
//    Page<FeePayment> findByStudentFeeAssignmentIdOrderByPaymentDateDesc(
//            Long assignmentId, Pageable pageable);
//
//    @Query("SELECT SUM(fp.amount) FROM FeePayment fp " +
//            "WHERE fp.studentFeeAssignment.id = :assignmentId " +
//            "AND fp.feeHead.id = :feeHeadId " +
//            "AND fp.status = 'PAID'")
//    BigDecimal getTotalPaidByAssignmentAndFeeHead(
//            @Param("assignmentId") Long assignmentId, @Param("feeHeadId") Long feeHeadId);
//
//    @Query("SELECT fp FROM FeePayment fp " +
//            "JOIN FETCH fp.studentFeeAssignment sfa " +
//            "JOIN FETCH sfa.studentEnrollment se " +
//            "JOIN FETCH fp.feeHead fh " +
//            "WHERE se.student.id = :studentId " +
//            "ORDER BY fp.paymentDate DESC")
//    Page<FeePayment> findByStudentIdWithDetails(@Param("studentId") Long studentId, Pageable pageable);
//}
//
