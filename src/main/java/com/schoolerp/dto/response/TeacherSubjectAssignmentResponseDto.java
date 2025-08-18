package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.entity.SectionSubjectAssignment;
import com.schoolerp.entity.Teacher;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class TeacherSubjectAssignmentResponseDto extends BaseDTO {

    private TeacherResponseDto teacher;
    private boolean isClassTeacher;
    private LocalDate assignedDate;
    private SectionSubjectAssignmentResponseDto sectionSubjectAssignment;

    public boolean isClassTeacher() {
        return isClassTeacher;
    }

    public SectionSubjectAssignmentResponseDto getSectionSubjectAssignment() {
        return sectionSubjectAssignment;
    }

    public void setSectionSubjectAssignment(SectionSubjectAssignmentResponseDto sectionSubjectAssignment) {
        this.sectionSubjectAssignment = sectionSubjectAssignment;
    }

    public boolean getClassTeacher() {
        return isClassTeacher;
    }

    public void setClassTeacher(boolean classTeacher) {
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
}

