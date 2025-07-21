package com.schoolerp.dto.request;
import com.schoolerp.enums.ResultStatus;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record ExamResultCreateDto(
        @NotNull Long studentId,
        @NotNull Long examId,
        @NotNull Long subjectId,
        @NotNull BigDecimal score,

        @NotNull BigDecimal totalMarks,

        ResultStatus status
) {}