package com.schoolerp.dto.request;

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
public class FeeStructureItemRequest {
    private Long feeHeadId;
    private BigDecimal amount;
    private LocalDate dueDate;
    private Boolean isActive;
}