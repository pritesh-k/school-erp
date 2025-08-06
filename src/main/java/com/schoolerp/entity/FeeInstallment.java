package com.schoolerp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fee_installments") //This supports flexible due dates & partial payments.
@Builder
public class FeeInstallment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private FeeStructureItem feeStructureItem;

    private String installmentName; // e.g., Q1, Q2, Jan, etc.

    private BigDecimal amount;
    private LocalDate dueDate;

    public FeeStructureItem getFeeStructureItem() {
        return feeStructureItem;
    }

    public void setFeeStructureItem(FeeStructureItem feeStructureItem) {
        this.feeStructureItem = feeStructureItem;
    }

    public String getInstallmentName() {
        return installmentName;
    }

    public void setInstallmentName(String installmentName) {
        this.installmentName = installmentName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}

