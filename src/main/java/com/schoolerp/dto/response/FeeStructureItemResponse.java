package com.schoolerp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeStructureItemResponse {
    private Long id;
    private Long feeHeadId;
    private String feeHeadName;
    private BigDecimal amount;
    private LocalDate dueDate;
    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFeeHeadId() {
        return feeHeadId;
    }

    public void setFeeHeadId(Long feeHeadId) {
        this.feeHeadId = feeHeadId;
    }

    public String getFeeHeadName() {
        return feeHeadName;
    }

    public void setFeeHeadName(String feeHeadName) {
        this.feeHeadName = feeHeadName;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}