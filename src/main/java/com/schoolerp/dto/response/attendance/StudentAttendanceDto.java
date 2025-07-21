package com.schoolerp.dto.response.attendance;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentAttendanceDto {
    private Long studentId;
    private String studentName;
    private String rollNumber;
    private String sectionName;
    private Double attendancePercentage;
    private Long totalDays;
    private Long presentDays;
    private Long absentDays;
}