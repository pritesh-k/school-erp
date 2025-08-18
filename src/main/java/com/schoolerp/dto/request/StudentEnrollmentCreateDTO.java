package com.schoolerp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StudentEnrollmentCreateDTO {

    @NotNull
    @Min(value = 1, message = "Student ID must be greater than 0")
    private Long studentId;

    @NotNull(message = "Class ID is required")
    @Min(value = 1, message = "Class ID must be greater than 0")
    private Long schoolClassId;

    @NotNull(message = "Section ID is required")
    @Min(value = 1, message = "Section ID must be greater than 0")
    private Long sectionId;

    @NotBlank(message = "Academic Session is required") @JsonProperty("academicSessionId")
    private String academicSessionName;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(Long schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getAcademicSessionName() {
        return academicSessionName;
    }

    public void setAcademicSessionName(String academicSessionName) {
        this.academicSessionName = academicSessionName;
    }
}
