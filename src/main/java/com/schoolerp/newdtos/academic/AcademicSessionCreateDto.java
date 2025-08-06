package com.schoolerp.newdtos.academic;

import com.schoolerp.enums.SessionStatus;
import com.schoolerp.validation.ValidAcademicYear;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

// Request DTO
public record AcademicSessionCreateDto(
        @NotBlank @ValidAcademicYear String name,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate
) {}
