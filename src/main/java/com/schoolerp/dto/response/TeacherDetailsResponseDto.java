package com.schoolerp.dto.response;

public class TeacherDetailsResponseDto {
    private TeacherResponseDto teacherResponseDto;
    private SectionSubjectAssignmentResponseDto sectionSubjectAssignmentResponseDto;


    public TeacherResponseDto getTeacherResponseDto() {
        return teacherResponseDto;
    }

    public void setTeacherResponseDto(TeacherResponseDto teacherResponseDto) {
        this.teacherResponseDto = teacherResponseDto;
    }

    public SectionSubjectAssignmentResponseDto getSectionSubjectAssignmentResponseDto() {
        return sectionSubjectAssignmentResponseDto;
    }

    public void setSectionSubjectAssignmentResponseDto(SectionSubjectAssignmentResponseDto sectionSubjectAssignmentResponseDto) {
        this.sectionSubjectAssignmentResponseDto = sectionSubjectAssignmentResponseDto;
    }
}
