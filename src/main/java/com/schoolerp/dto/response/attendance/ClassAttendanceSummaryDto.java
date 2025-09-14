package com.schoolerp.dto.response.attendance;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClassAttendanceSummaryDto {
    private Long classId;
    private String className;
    private long totalStudents;
    private long presentCount;
    private long absentCount;
    private long lateCount;
    private long leaveCount;
    private double percentagePresent;
    private int totalSections;
}