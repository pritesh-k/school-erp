package com.schoolerp.dto.response.attendance;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AttendanceSummaryDto {
    private Long totalDays;
    private Long presentDays;
    private Long absentDays;
    private Long lateDays;
    private Double attendancePercentage;
    private LocalDate fromDate;
    private LocalDate toDate;
}