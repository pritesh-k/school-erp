package com.schoolerp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.java.Log;

import java.math.BigDecimal;

public class FeeAssignDto {
    @NotNull
    public Long feeStructureId;
    public BigDecimal discountAmount;
    private String discountReason;

    @NotNull
    private Long studentEnrollmentId;

    public Long getStudentEnrollmentId() {
        return studentEnrollmentId;
    }

    public void setStudentEnrollmentId(Long studentEnrollmentId) {
        this.studentEnrollmentId = studentEnrollmentId;
    }

    public Long getFeeStructureId() {
        return feeStructureId;
    }

    public void setFeeStructureId(Long feeStructureId) {
        this.feeStructureId = feeStructureId;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDiscountReason() {
        return discountReason;
    }

    public void setDiscountReason(String discountReason) {
        this.discountReason = discountReason;
    }
}
