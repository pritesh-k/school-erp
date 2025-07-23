package com.schoolerp.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeePaymentDto {
    public Long studentFeeAssignmentId;
    public Long feeHeadId;
    public BigDecimal paidAmount;
    public String note;

    public Long getStudentFeeAssignmentId() {
        return studentFeeAssignmentId;
    }

    public void setStudentFeeAssignmentId(Long studentFeeAssignmentId) {
        this.studentFeeAssignmentId = studentFeeAssignmentId;
    }

    public Long getFeeHeadId() {
        return feeHeadId;
    }

    public void setFeeHeadId(Long feeHeadId) {
        this.feeHeadId = feeHeadId;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
