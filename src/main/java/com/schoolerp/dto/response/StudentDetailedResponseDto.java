package com.schoolerp.dto.response;

public class StudentDetailedResponseDto {
    private StudentResponseDto student;
    private ParentResponseDto parent;

    public StudentResponseDto getStudent() {
        return student;
    }

    public void setStudent(StudentResponseDto student) {
        this.student = student;
    }

    public ParentResponseDto getParent() {
        return parent;
    }

    public void setParent(ParentResponseDto parent) {
        this.parent = parent;
    }
}
