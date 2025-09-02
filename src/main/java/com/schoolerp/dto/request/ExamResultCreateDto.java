package com.schoolerp.dto.request;
import com.schoolerp.enums.ResultStatus;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ExamResultCreateDto {
    @NotNull private Long studentEnrollmentId;
    @NotNull private BigDecimal score;
    @NotNull private BigDecimal totalMarks;
    private String grade;
    @NotNull private ResultStatus status;

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