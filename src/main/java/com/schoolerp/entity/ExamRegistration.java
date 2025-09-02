package com.schoolerp.entity;

import jakarta.persistence.*;

@Entity
@Table(name="exam_registrations",
        uniqueConstraints = @UniqueConstraint(name="uk_reg_student_slot", columnNames={"student_id","exam_slot_id"}),
        indexes = {
                @Index(name="idx_reg_slot", columnList="exam_slot_id"),
                @Index(name="idx_reg_student", columnList="student_enrollment_id")
        })
public class ExamRegistration extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_enrollment_id", nullable=false)
    private StudentEnrollment studentEnrollment;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="exam_slot_id", nullable=false)
    private ExamSlot examSlot;

    public StudentEnrollment getStudentEnrollment() {
        return studentEnrollment;
    }

    public void setStudentEnrollment(StudentEnrollment studentEnrollment) {
        this.studentEnrollment = studentEnrollment;
    }

    public ExamSlot getExamSlot() {
        return examSlot;
    }

    public void setExamSlot(ExamSlot examSlot) {
        this.examSlot = examSlot;
    }
}
