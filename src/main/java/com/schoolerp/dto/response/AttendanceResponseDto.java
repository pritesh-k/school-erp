package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.entity.Section;
import com.schoolerp.entity.Student;
import com.schoolerp.entity.Teacher;
import com.schoolerp.enums.AttendanceStatus;

import java.time.LocalDate;

public class AttendanceResponseDto extends BaseDTO {

    private Student student;

    private Section section;

    private LocalDate date;
    private AttendanceStatus status;
    private String remarks;
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
