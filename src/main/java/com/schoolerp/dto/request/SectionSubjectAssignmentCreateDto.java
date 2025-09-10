package com.schoolerp.dto.request;

import jakarta.validation.constraints.NotNull;

public class SectionSubjectAssignmentCreateDto {

    @NotNull
    private Long subjectId;

    @NotNull
    private Long classId;
    private boolean isMandatory = false;
    private Integer weeklyHours;

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public boolean  getMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public Integer getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(Integer weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public boolean isMandatory() {
        return isMandatory;
    }
}
