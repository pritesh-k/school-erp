package com.schoolerp.entity;

import com.schoolerp.enums.SubjectCategory;
import com.schoolerp.enums.SubjectCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "subjects",
        indexes = {
                @Index(name = "idx_subject_category", columnList = "category")
        }
)
@NoArgsConstructor @AllArgsConstructor @Builder
public class Subject extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private SubjectCode code;

    // This shows which classes can study this subject
    @ManyToMany
    @JoinTable(name = "subject_class",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id"))
    private Set<SchoolClass> classes = new HashSet<>();

    // Section-level subject offerings (e.g., 9A offers Physics)
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SectionSubjectAssignment> sectionSubjectAssignments = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private SubjectCategory category;

    public SubjectCode getCode() {
        return code;
    }

    public void setCode(SubjectCode code) {
        this.code = code;
    }

    public SubjectCategory getCategory() {
        return category;
    }

    public void setCategory(SubjectCategory category) {
        this.category = category;
    }

    public Set<SchoolClass> getClasses() {
        return classes;
    }

    public void setClasses(Set<SchoolClass> classes) {
        this.classes = classes;
    }

    public Set<SectionSubjectAssignment> getSectionSubjectAssignments() {
        return sectionSubjectAssignments;
    }

    public void setSectionSubjectAssignments(Set<SectionSubjectAssignment> sectionSubjectAssignments) {
        this.sectionSubjectAssignments = sectionSubjectAssignments;
    }
}