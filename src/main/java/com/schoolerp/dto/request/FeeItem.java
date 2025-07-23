package com.schoolerp.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeeItem {
    private Long headId;
    private BigDecimal amount;

    public Long getHeadId() {
        return headId;
    }

    public void setHeadId(Long headId) {
        this.headId = headId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
