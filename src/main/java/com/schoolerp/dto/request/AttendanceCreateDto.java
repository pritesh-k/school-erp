package com.schoolerp.dto.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.schoolerp.enums.AttendanceStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
public class AttendanceCreateDto {
    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Section ID is required")
    private Long sectionId;

    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "Status is required")
    private AttendanceStatus status;

    @Size(max = 500, message = "Remarks cannot exceed 500 characters")
    private String remarks;

    @NotNull(message = "Recorded by teacher ID is required")
    private Long recordedById;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getRecordedById() {
        return recordedById;
    }

    public void setRecordedById(Long recordedById) {
        this.recordedById = recordedById;
    }
}
