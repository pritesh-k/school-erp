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

    @ManyToMany
    @JoinTable(name = "subject_class",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id"))
    private Set<SchoolClass> classes = new HashSet<>();

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectAssignment> assignments = new HashSet<>();

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

    public Set<SubjectAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<SubjectAssignment> assignments) {
        this.assignments = assignments;
    }
}