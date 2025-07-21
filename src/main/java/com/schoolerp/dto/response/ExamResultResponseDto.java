package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.entity.Exam;
import com.schoolerp.entity.Student;
import com.schoolerp.entity.Subject;
import com.schoolerp.enums.ResultStatus;

import java.math.BigDecimal;
public class ExamResultResponseDto extends BaseDTO {
    private Student student;
    private Exam exam;
    private Subject subject;
    private BigDecimal score; // marks obtained
    private BigDecimal totalMarks; // total possible marks for this subject
    private String grade;
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