package com.schoolerp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeacherSubjectAssignmentCreateDto {
    private Long sectionSubjectAssignmentId;

    @JsonProperty("isClassTeacher")
    private boolean isClassTeacher;

    public Long getSectionSubjectAssignmentId() {
        return sectionSubjectAssignmentId;
    }

    public void setSectionSubjectAssignmentId(Long sectionSubjectAssignmentId) {
        this.sectionSubjectAssignmentId = sectionSubjectAssignmentId;
    }

    public boolean getClassTeacher() {
        return isClassTeacher;
    }

    public void setClassTeacher(boolean classTeacher) {
        isClassTeacher = classTeacher;
    }
}
