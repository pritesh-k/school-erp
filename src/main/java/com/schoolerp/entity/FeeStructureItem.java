package com.schoolerp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    private BigDecimal amount;
    // Can add schedule (monthly/quarterly/annual), dueDate, etc


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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

