package com.schoolerp.dto.request;

public class TeacherSubjectAssignmentCreateDto {
    private Long sectionSubjectAssignmentId;
    private Boolean isClassTeacher;

    public Long getSectionSubjectAssignmentId() {
        return sectionSubjectAssignmentId;
    }

    public void setSectionSubjectAssignmentId(Long sectionSubjectAssignmentId) {
        this.sectionSubjectAssignmentId = sectionSubjectAssignmentId;
    }

    public Boolean getClassTeacher() {
        return isClassTeacher;
    }

    public void setClassTeacher(Boolean classTeacher) {
        isClassTeacher = classTeacher;
    }
}
