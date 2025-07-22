package com.schoolerp.entity;

import com.schoolerp.enums.SubjectCategory;
import com.schoolerp.enums.SubjectCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subjects")
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

    @ManyToMany
    @JoinTable(name = "subject_teacher",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Teacher> teachersAssigned = new HashSet<>();

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

    public Set<Teacher> getTeachersAssigned() {
        return teachersAssigned;
    }

    public void setTeachersAssigned(Set<Teacher> teachersAssigned) {
        this.teachersAssigned = teachersAssigned;
    }
}