package com.schoolerp.dto.response.attendance;

import com.schoolerp.enums.ClassStandard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyAttendanceReportDto {

    private LocalDate weekStart;           // inclusive
    private LocalDate weekEnd;             // inclusive (start+6)

    private Long classId;
    private ClassStandard className;

    private Long studentId;
    private String studentName;

    private long totalRecords;
    private long presentDays;
    private long absentDays;
    private long lateDays;
    private double percentage;

    public LocalDate getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(LocalDate weekStart) {
        this.weekStart = weekStart;
    }
}

