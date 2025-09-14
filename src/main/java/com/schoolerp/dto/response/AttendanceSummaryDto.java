package com.schoolerp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class AttendanceSummaryDto {

    private LocalDate date;
    private Long classId;
    private String className;
    private Long sectionId;
    private String sectionName;
    private Long totalStudents;
    private Long presentCount;
    private Long absentCount;
    private Long lateCount;
    private Long leaveCount;
    private Double percentagePresent;

    // All-args constructor
    public AttendanceSummaryDto(LocalDate date, Long classId, String className,
                                Long sectionId, String sectionName, Long totalStudents,
                                Long presentCount, Long absentCount, Long lateCount,
                                Long leaveCount, Double percentagePresent) {
        this.date = date;
        this.classId = classId;
        this.className = className;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.totalStudents = totalStudents != null ? totalStudents : 0L;
        this.presentCount = presentCount != null ? presentCount : 0L;
        this.absentCount = absentCount != null ? absentCount : 0L;
        this.lateCount = lateCount != null ? lateCount : 0L;
        this.leaveCount = leaveCount != null ? leaveCount : 0L;
        this.percentagePresent = percentagePresent != null ? percentagePresent : 0.0;
    }
}
