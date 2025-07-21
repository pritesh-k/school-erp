package com.schoolerp.entity;

import com.schoolerp.enums.AttendanceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "section_id", "date"}))
@NoArgsConstructor @AllArgsConstructor @Builder
public class Attendance extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Section section;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
    private String remarks;
    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher recordedBy;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
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

    public Teacher getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(Teacher recordedBy) {
        this.recordedBy = recordedBy;
    }
}