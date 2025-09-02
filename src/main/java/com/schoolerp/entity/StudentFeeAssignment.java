//package com.schoolerp.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.NoArgsConstructor;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "student_fee_assignments",
//        uniqueConstraints = @UniqueConstraint(columnNames = {"student_enrollment_id", "fee_structure_id"}),
//        indexes = {
//                @Index(name = "idx_student_enrollment", columnList = "student_enrollment_id"),
//                @Index(name = "idx_fee_structure", columnList = "fee_structure_id")
//        })
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class StudentFeeAssignment extends BaseEntity {
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "student_enrollment_id")
//    private StudentEnrollment studentEnrollment;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "fee_structure_id")
//    private FeeStructure feeStructure;
//
//    @Column(precision = 10, scale = 2)
//    private BigDecimal discountAmount = BigDecimal.ZERO;
//
//    @Column(length = 500)
//    private String discountReason;
//
//    private LocalDate assignedDate;
//
//    @OneToMany(mappedBy = "studentFeeAssignment", cascade = CascadeType.ALL)
//    private Set<FeePayment> payments = new HashSet<>();
//
//    public StudentEnrollment getStudentEnrollment() {
//        return studentEnrollment;
//    }
//
//    public void setStudentEnrollment(StudentEnrollment studentEnrollment) {
//        this.studentEnrollment = studentEnrollment;
//    }
//
//    public FeeStructure getFeeStructure() {
//        return feeStructure;
//    }
//
//    public void setFeeStructure(FeeStructure feeStructure) {
//        this.feeStructure = feeStructure;
//    }
//
//    public BigDecimal getDiscountAmount() {
//        return discountAmount;
//    }
//
//    public void setDiscountAmount(BigDecimal discountAmount) {
//        this.discountAmount = discountAmount;
//    }
//
//    public String getDiscountReason() {
//        return discountReason;
//    }
//
//    public void setDiscountReason(String discountReason) {
//        this.discountReason = discountReason;
//    }
//
//    public LocalDate getAssignedDate() {
//        return assignedDate;
//    }
//
//    public void setAssignedDate(LocalDate assignedDate) {
//        this.assignedDate = assignedDate;
//    }
//
//    public Set<FeePayment> getPayments() {
//        return payments;
//    }
//
//    public void setPayments(Set<FeePayment> payments) {
//        this.payments = payments;
//    }
//}