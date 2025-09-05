package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
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
public class FeeStructureItemResponse extends BaseDTO {
    private BigDecimal amount;
    private LocalDate dueDate;

    private FeeHeadResponse feeHead;

    public FeeHeadResponse getFeeHead() {
        return feeHead;
    }

    public void setFeeHead(FeeHeadResponse feeHead) {
        this.feeHead = feeHead;
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