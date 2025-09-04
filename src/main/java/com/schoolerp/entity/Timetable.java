package com.schoolerp.entity;

import com.schoolerp.enums.TimetableType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "timetables")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Timetable extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "academic_session_id", nullable = false)
    private AcademicSession academicSession;

    // Type of schedule: PERIOD, EXAM, ACTIVITY, HOLIDAY etc.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimetableType type;

    // Which class,section,subject is this scheduled for?
    @ManyToOne(fetch = FetchType.LAZY)
    private SectionSubjectAssignment sectionSubjectAssignment;

    // If this event is an exam slot, link to the exam
    @ManyToOne(fetch = FetchType.LAZY)
    private Exam exam;

    // Scheduled date/time
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimeTableStatus status; // DRAFT, ACTIVE, CANCELLED

    // Location, e.g., Room No/Lab (optional)
    private String location;

    // Notes/extra info (optional)
    private String note;

    public TimetableType getType() {
        return type;
    }

    public void setType(TimetableType type) {
        this.type = type;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
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

    public SectionSubjectAssignment getSectionSubjectAssignment() {
        return sectionSubjectAssignment;
    }

    public void setSectionSubjectAssignment(SectionSubjectAssignment sectionSubjectAssignment) {
        this.sectionSubjectAssignment = sectionSubjectAssignment;
    }

    public AcademicSession getAcademicSession() {
        return academicSession;
    }

    public void setAcademicSession(AcademicSession academicSession) {
        this.academicSession = academicSession;
    }

    public TimeTableStatus getStatus() {
        return status;
    }

    public void setStatus(TimeTableStatus status) {
        this.status = status;
    }

    public enum TimeTableStatus {
        DRAFT, ACTIVE, CANCELLED
    }
}
