package com.schoolerp.dto.request;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record FeeTypeCreateDto(
        @NotBlank String name,
        @NotNull BigDecimal defaultAmount
) {}