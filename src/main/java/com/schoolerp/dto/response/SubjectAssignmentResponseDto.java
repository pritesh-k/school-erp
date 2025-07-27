package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.entity.Subject;

import java.time.LocalDate;

public class SubjectAssignmentResponseDto extends BaseDTO {
    TeacherResponseDto teacher;
    SubjectResponseDto subject;
    SectionResponseDto section;
    LocalDate assignedDate;

    public TeacherResponseDto getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherResponseDto teacher) {
        this.teacher = teacher;
    }

    public SubjectResponseDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectResponseDto subject) {
        this.subject = subject;
    }

    public SectionResponseDto getSection() {
        return section;
    }

    public void setSection(SectionResponseDto section) {
        this.section = section;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }
}
