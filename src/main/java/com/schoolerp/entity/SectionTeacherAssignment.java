package com.schoolerp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;

import java.time.LocalDate;

@Entity
@Table(name = "section_teacher_assignment")
@Builder
public class SectionTeacherAssignment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    private Boolean isClassTeacher;  // true if this teacher is the class teacher of the section
    private LocalDate assignedDate;

    // any other fields (subject?, notes?, creator?)


    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

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

