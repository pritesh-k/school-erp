package com.schoolerp.entity;

import com.schoolerp.enums.ExamStatus;
import com.schoolerp.enums.Term;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "exams",
        indexes = {
                @Index(name = "idx_exam_session", columnList = "academic_session_id"),
                @Index(name = "idx_exam_status", columnList = "status")
        })
public class Exam extends BaseEntity {

    @Column(nullable = false, length = 120)
    private String name;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Term term;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "academic_session_id", nullable = false)
    private AcademicSession academicSession;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private ExamStatus status; // DRAFT, PUBLISHED, ARCHIVED

    private Long publishedBy;

    @PrePersist @PreUpdate
    private void validateWindow() {
        if (endDate.isBefore(startDate)) throw new IllegalArgumentException("End date before start date");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public AcademicSession getAcademicSession() {
        return academicSession;
    }

    public void setAcademicSession(AcademicSession academicSession) {
        this.academicSession = academicSession;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ExamStatus getStatus() {
        return status;
    }

    public void setStatus(ExamStatus status) {
        this.status = status;
    }

    public Long getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(Long publishedBy) {
        this.publishedBy = publishedBy;
    }
}
