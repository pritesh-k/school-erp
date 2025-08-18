package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.entity.Section;
import com.schoolerp.entity.Student;
import com.schoolerp.entity.Teacher;
import com.schoolerp.enums.AttendanceStatus;

import java.time.LocalDate;

public class AttendanceResponseDto extends BaseDTO {

    private StudentResponseDto student;
    private SectionResponseDto section;
    private String academicSessionName;
    private LocalDate date;
    private AttendanceStatus status;
    private String remarks;

    private TeacherResponseDto recordedBy;

    public StudentResponseDto getStudent() {
        return student;
    }

    public void setStudent(StudentResponseDto student) {
        this.student = student;
    }

    public SectionResponseDto getSection() {
        return section;
    }

    public void setSection(SectionResponseDto section) {
        this.section = section;
    }

    public String getAcademicSessionName() {
        return academicSessionName;
    }

    public void setAcademicSessionName(String academicSessionName) {
        this.academicSessionName = academicSessionName;
    }

    public TeacherResponseDto getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(TeacherResponseDto recordedBy) {
        this.recordedBy = recordedBy;
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

}
