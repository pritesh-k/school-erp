package com.schoolerp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherAssignmentDto {
    private Long teacherId;
    private String teacherName;
    private String employeeCode;
    private List<SectionSummaryDto> assignedSections;
    private List<SubjectSummaryDto> assignedSubjects;

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public List<SectionSummaryDto> getAssignedSections() {
        return assignedSections;
    }

    public void setAssignedSections(List<SectionSummaryDto> assignedSections) {
        this.assignedSections = assignedSections;
    }

    public List<SubjectSummaryDto> getAssignedSubjects() {
        return assignedSubjects;
    }

    public void setAssignedSubjects(List<SubjectSummaryDto> assignedSubjects) {
        this.assignedSubjects = assignedSubjects;
    }
}
