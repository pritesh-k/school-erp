package com.schoolerp.entity;

import com.schoolerp.enums.ResultStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "exam_results")
@NoArgsConstructor @AllArgsConstructor @Builder
public class ExamResult extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    private BigDecimal score; // marks obtained

    private BigDecimal totalMarks; // total possible marks for this subject

    private String grade;

    @Enumerated(EnumType.STRING)
    private ResultStatus status;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(BigDecimal totalMarks) {
        this.totalMarks = totalMarks;
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