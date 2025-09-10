package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;

public class ClassTeacherAssignmentResponse extends BaseDTO {
    private TeacherResponseDto teacher;
    private SectionResponseDto section;

    public TeacherResponseDto getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherResponseDto teacher) {
        this.teacher = teacher;
    }

    public SectionResponseDto getSection() {
        return section;
    }

    public void setSection(SectionResponseDto section) {
        this.section = section;
    }
}
