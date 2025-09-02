package com.schoolerp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class FeePaymentDTO {
    private Long id;
    private Long studentFeeAssignmentId;
    private Long feeHeadId;
    private String feeHeadName;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String paymentMode;
    private String status;
    private String transactionReference;
    private String receivedBy;
}

