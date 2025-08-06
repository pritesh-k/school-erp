package com.schoolerp.entity;

import com.schoolerp.enums.PaymentFrequency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fee_structure_items")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeStructureItem extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private FeeStructure feeStructure;

    @ManyToOne(fetch = FetchType.LAZY)
    private FeeHead feeHead;

    private BigDecimal totalAmount;

    // Optional but good for reporting
    @Enumerated(EnumType.STRING)
    private PaymentFrequency frequency; // MONTHLY, QUARTERLY, ANNUAL

    // One-time due date (optional if not using installments)
    private LocalDate dueDate;

    @OneToMany(mappedBy = "feeStructureItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FeeInstallment> installments = new HashSet<>();

    public FeeStructure getFeeStructure() {
        return feeStructure;
    }

    public void setFeeStructure(FeeStructure feeStructure) {
        this.feeStructure = feeStructure;
    }

    public FeeHead getFeeHead() {
        return feeHead;
    }

    public void setFeeHead(FeeHead feeHead) {
        this.feeHead = feeHead;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(PaymentFrequency frequency) {
        this.frequency = frequency;
    }

    public Set<FeeInstallment> getInstallments() {
        return installments;
    }

    public void setInstallments(Set<FeeInstallment> installments) {
        this.installments = installments;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}

