package com.schoolerp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "teacher_subject_assignments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherSubjectAssignment extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne(optional = false)
    @JoinColumn(name = "section_subject_assignment_id", nullable = false)
    private SectionSubjectAssignment sectionSubjectAssignment;

    private LocalDate assignedDate;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public SectionSubjectAssignment getSectionSubjectAssignment() {
        return sectionSubjectAssignment;
    }

    public void setSectionSubjectAssignment(SectionSubjectAssignment sectionSubjectAssignment) {
        this.sectionSubjectAssignment = sectionSubjectAssignment;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }
}


