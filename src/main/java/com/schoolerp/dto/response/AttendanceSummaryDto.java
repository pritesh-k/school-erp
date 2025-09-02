package com.schoolerp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class AttendanceSummaryDto {
    private LocalDate date;
    private long totalStudents;
    private long presentCount;
    private long absentCount;
    private long lateCount;
    private long leaveCount;
    private double percentagePresent;
}
