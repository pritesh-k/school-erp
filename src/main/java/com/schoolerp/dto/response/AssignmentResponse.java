package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;

import java.time.LocalDate;

public class AssignmentResponse extends BaseDTO {

    private TeacherResponseDto teacher;
    private SectionResponseDto section;
    private Boolean isClassTeacher;
    private LocalDate assignedDate;

    public Boolean getClassTeacher() {
        return isClassTeacher;
    }

    public void setClassTeacher(Boolean classTeacher) {
        isClassTeacher = classTeacher;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public TeacherResponseDto getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherResponseDto teacher) {
        this.teacher = teacher;
    }

    public SectionResponseDto getSection() {
        return section;
    }

    public void setSection(SectionResponseDto section) {
        this.section = section;
    }
}
