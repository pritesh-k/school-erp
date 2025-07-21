package com.schoolerp.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeacherAssignmentDto {
    private Long teacherId;
    private String teacherName;
    private String employeeCode;
    private List<SectionSummaryDto> assignedSections;
    private List<SubjectSummaryDto> assignedSubjects;
}
