package com.schoolerp.dto.request;

import jakarta.validation.constraints.NotNull;

public class ExamRegistrationCreateDto {
    @NotNull
    private Long studentEnrollmentId;

    public Long getStudentEnrollmentId() {
        return studentEnrollmentId;
    }

    public void setStudentEnrollmentId(Long studentEnrollmentId) {
        this.studentEnrollmentId = studentEnrollmentId;
    }
}
