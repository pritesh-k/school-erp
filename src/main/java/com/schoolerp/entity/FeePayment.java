package com.schoolerp.entity;

import com.schoolerp.enums.PaymentMode;
import com.schoolerp.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fee_payments",
        indexes = {
                @Index(name = "idx_student_assignment", columnList = "student_fee_assignment_id"),
                @Index(name = "idx_fee_head", columnList = "fee_head_id"),
                @Index(name = "idx_payment_date", columnList = "payment_date")
        })
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeePayment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_fee_assignment_id")
    private StudentFeeAssignment studentFeeAssignment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fee_head_id")
    private FeeHead feeHead;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode; // CASH, ONLINE, CHEQUE

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // PAID, PENDING, CANCELLED

    @Column(length = 100)
    private String transactionReference;

    @Column(length = 1000)
    private String remarks;

    @Column(length = 100)
    private String receivedBy;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }
}
