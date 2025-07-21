package com.schoolerp.dto.request;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
public record FeeRecordCreateDto(
        @NotNull Long studentId,
        @NotNull Long feeTypeId,
        @NotNull BigDecimal amount,
        @NotNull LocalDate dueDate
) {}