package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.entity.Timetable;
import com.schoolerp.enums.TimetableType;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimetableDetailedResponseDTO extends BaseDTO {
    private SectionSubjectAssignmentResponseDto sectionSubjectAssignment;

    private TimetableType type;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Timetable.TimeTableStatus status;

    private String location;
    private String note;

    private Long examId;
    private String examName;
    public SectionSubjectAssignmentResponseDto getSectionSubjectAssignment() {
        return sectionSubjectAssignment;
    }

    public void setSectionSubjectAssignment(SectionSubjectAssignmentResponseDto sectionSubjectAssignment) {
        this.sectionSubjectAssignment = sectionSubjectAssignment;
    }

    public TimetableType getType() {
        return type;
    }

    public void setType(TimetableType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Timetable.TimeTableStatus getStatus() {
        return status;
    }

    public void setStatus(Timetable.TimeTableStatus status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }
}
