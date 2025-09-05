package com.schoolerp.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FeeStructureItemUpdateRequest {
    private BigDecimal amount;
    private LocalDate dueDate;

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
