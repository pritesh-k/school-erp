package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.entity.SectionSubjectAssignment;
import com.schoolerp.entity.Teacher;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class TeacherSubjectAssignmentResponseDto extends BaseDTO {

    private TeacherResponseDto teacher;
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
}

