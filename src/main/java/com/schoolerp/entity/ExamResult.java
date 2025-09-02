package com.schoolerp.entity;

import com.schoolerp.enums.ResultStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "exam_results",
        uniqueConstraints = @UniqueConstraint(name="uk_result_student_slot",
                columnNames={"student_enrollment_id","exam_slot_id"}))
@NoArgsConstructor @AllArgsConstructor @Builder
public class ExamResult extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_enrollment_id", nullable = false)
    private StudentEnrollment studentEnrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_slot_id", nullable = false)
    private ExamSlot examSlot;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal score; // marks obtained

    private String grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultStatus status; // e.g., PASSED, FAILED, ABSENT

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

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }
}
