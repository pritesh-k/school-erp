package com.schoolerp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class BulkStudentEnrollmentCreateDTO {

    @NotNull(message = "Class ID is required")
    @Min(value = 1, message = "Class ID must be greater than 0")
    private long schoolClassId;

    @NotNull(message = "Section ID is required")
    @Min(value = 1, message = "Section ID must be greater than 0")
    private long sectionId;
    @NotEmpty(message = "At least one student ID is required")
    private List<@Min(value = 1, message = "Student ID must be greater than 0") Long> studentIds;

    public long getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(long schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }
    public List<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }
}
