package com.schoolerp.dto.request;

public class SubjectAssignmentUpdateDto {
    private Long id;
    private Long teacherId; // allows reassigning teacher

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
