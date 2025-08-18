package com.schoolerp.dto.response;

import com.schoolerp.enums.AttendanceStatus;

import java.util.Map;

public class AttendancePercentageReportDto {
    private String academicSessionName;
    private long totalRecords;
    private Map<AttendanceStatus, Double> percentageByStatus; // e.g. {PRESENT: 92.5, ABSENT: 5.0, LATE: 2.5}

    public String getAcademicSessionName() {
        return academicSessionName;
    }

    public void setAcademicSessionName(String academicSessionName) {
        this.academicSessionName = academicSessionName;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Map<AttendanceStatus, Double> getPercentageByStatus() {
        return percentageByStatus;
    }

    public void setPercentageByStatus(Map<AttendanceStatus, Double> percentageByStatus) {
        this.percentageByStatus = percentageByStatus;
    }
}

