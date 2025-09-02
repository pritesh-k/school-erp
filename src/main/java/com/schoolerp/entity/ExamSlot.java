package com.schoolerp.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "exam_slots",
        uniqueConstraints = {
                // Prevent duplicate “same exam+class+section+subject at same time”
                @UniqueConstraint(name = "uk_slot_unique",
                        columnNames = {"exam_id","school_class_id","section_id","subject_id","date","start_time"})
        },
        indexes = {
                @Index(name="idx_slot_exam", columnList = "exam_id"),
                @Index(name="idx_slot_class", columnList = "school_class_id, section_id"),
                @Index(name="idx_slot_subject", columnList = "subject_id"),
                @Index(name="idx_slot_date", columnList = "date")
        })
public class ExamSlot extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "school_class_id", nullable = false)
    private SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "section_id")
    private Section section; // nullable if slot is for entire class

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name="start_time", nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private Integer durationMinutes; // compute endTime = start + duration

    @Column(nullable = false)
    private Integer maxMarks;

    @Column(nullable = false)
    private Boolean practical = false;

    @PrePersist @PreUpdate
    private void validate() {
        if (durationMinutes == null || durationMinutes <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Integer getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Integer maxMarks) {
        this.maxMarks = maxMarks;
    }

    public Boolean getPractical() {
        return practical;
    }

    public void setPractical(Boolean practical) {
        this.practical = practical;
    }
}
