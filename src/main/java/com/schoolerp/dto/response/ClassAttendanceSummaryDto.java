package com.schoolerp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ClassAttendanceSummaryDto {
    private Long classId;
    private String className;
    private String sectionName;
    private LocalDate date;
    private long totalStudents;
    private long presentCount;
    private long absentCount;
    private double percentagePresent;
}
