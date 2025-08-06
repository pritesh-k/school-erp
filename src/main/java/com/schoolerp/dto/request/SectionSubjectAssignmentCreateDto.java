package com.schoolerp.dto.request;

import jakarta.validation.constraints.NotBlank;

public class SectionSubjectAssignmentCreateDto {

    @NotBlank
    private Long subjectId;
    private boolean isMandatory;
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
}
