package com.schoolerp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentFeeAssignmentRequest {
    private Long studentEnrollmentId;
    private Long feeStructureId;
    private BigDecimal discountAmount;
    private String discountReason;
    private Boolean isActive;
}
