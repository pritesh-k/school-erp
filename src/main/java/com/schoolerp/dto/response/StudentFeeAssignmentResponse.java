package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentFeeAssignmentResponse extends BaseDTO {
    private FeeStructureResponse feeStructure;
    private BigDecimal discountAmount;
    private String discountReason;
    private LocalDate assignedDate;

    public FeeStructureResponse getFeeStructure() {
        return feeStructure;
    }

    public void setFeeStructure(FeeStructureResponse feeStructure) {
        this.feeStructure = feeStructure;
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

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }
}