package com.schoolerp.dto.response.attendance;

import com.schoolerp.enums.ClassStandard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyAttendanceReportDto {

    private int year;
    private int month;                     // 1-12

    private Long classId;                  // optional
    private ClassStandard className;

    private Long studentId;                // optional
    private String studentName;

    private long totalRecords;             // rows fetched
    private long presentDays;
    private long absentDays;
    private long lateDays;
    private double percentage;             // rounded to 2-dp
}

