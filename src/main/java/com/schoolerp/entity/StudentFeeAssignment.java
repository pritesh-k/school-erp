package com.schoolerp.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.math.BigDecimal;

@Entity
@Table(name = "student_fee_assignments", uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "fee_structure_id"}))
@Builder
public class StudentFeeAssignment extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;
    @ManyToOne(fetch = FetchType.LAZY)
    private FeeStructure feeStructure;
    private BigDecimal discount; // if any
    // status: assigned/active/completed


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public FeeStructure getFeeStructure() {
        return feeStructure;
    }

    public void setFeeStructure(FeeStructure feeStructure) {
        this.feeStructure = feeStructure;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}

