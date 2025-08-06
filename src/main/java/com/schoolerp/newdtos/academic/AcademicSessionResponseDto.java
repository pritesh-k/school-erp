package com.schoolerp.newdtos.academic;

import com.schoolerp.enums.SessionStatus;

import java.time.LocalDate;

// Response DTO
public record AcademicSessionResponseDto(
        Long id,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        SessionStatus status,
        boolean isCurrent
) {}
