package com.schoolerp.dto.request;

import java.math.BigDecimal;

public class FeeAssignUpdateDto {
    public BigDecimal discountAmount;
    private String discountReason;

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
