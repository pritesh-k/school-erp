package com.schoolerp.dto.response.attendance;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendancePercentageDto {
    private Long studentId;
    private String studentName;
    private Double percentage;
    private Long totalDays;
    private Long presentDays;
    private Long absentDays;
    private Long lateDays;
}
