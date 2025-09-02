package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.entity.Exam;
import com.schoolerp.entity.Student;
import com.schoolerp.entity.Subject;
import com.schoolerp.enums.ResultStatus;

import java.math.BigDecimal;
public class ExamResultResponseDto extends BaseDTO {
    private Long examSlotId;
    private Long studentEnrollmentId;
    private BigDecimal score;
    private BigDecimal totalMarks;
    private String grade;
    private ResultStatus status;

    public Long getExamSlotId() {
        return examSlotId;
    }

    public void setExamSlotId(Long examSlotId) {
        this.examSlotId = examSlotId;
    }

    public Long getStudentEnrollmentId() {
        return studentEnrollmentId;
    }

    public void setStudentEnrollmentId(Long studentEnrollmentId) {
        this.studentEnrollmentId = studentEnrollmentId;
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