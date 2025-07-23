package com.schoolerp.entity;

import com.schoolerp.enums.FeeStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fee_records")
@NoArgsConstructor @AllArgsConstructor @Builder
public class FeeRecord extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private StudentFeeAssignment studentFeeAssignment;
    @ManyToOne(fetch = FetchType.LAZY)
    private FeeHead feeHead; // which component/head is being paid (e.g. tuition)
    private BigDecimal paidAmount;
    private LocalDate paidDate;
    private String receiptNo;
    @Enumerated(EnumType.STRING)
    private FeeStatus status; // PAID, PARTIAL, DUE, WAIVED, REFUNDED, etc.
    private String note;
    private String receiptUrl;

    public StudentFeeAssignment getStudentFeeAssignment() {
        return studentFeeAssignment;
    }

    public void setStudentFeeAssignment(StudentFeeAssignment studentFeeAssignment) {
        this.studentFeeAssignment = studentFeeAssignment;
    }

    public FeeHead getFeeHead() {
        return feeHead;
    }

    public void setFeeHead(FeeHead feeHead) {
        this.feeHead = feeHead;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public FeeStatus getStatus() {
        return status;
    }

    public void setStatus(FeeStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }
}