package com.schoolerp.entity;

import com.schoolerp.enums.FeeStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "fee_payments")
public class FeePayment extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    private FeeInstallment installment; // Can be nullable if not using installments

    @ManyToOne(fetch = FetchType.LAZY)
    private FeeHead feeHead;

    private BigDecimal paidAmount;

    @Enumerated(EnumType.STRING)
    private FeeStatus status; // PAID, PARTIAL, DUE, etc.

    @ManyToOne(fetch = FetchType.LAZY)
    private Receipt receipt;

    public FeeInstallment getInstallment() {
        return installment;
    }

    public void setInstallment(FeeInstallment installment) {
        this.installment = installment;
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

    public FeeStatus getStatus() {
        return status;
    }

    public void setStatus(FeeStatus status) {
        this.status = status;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}
