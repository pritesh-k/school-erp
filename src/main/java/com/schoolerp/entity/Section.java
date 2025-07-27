package com.schoolerp.entity;

import com.schoolerp.enums.SectionName;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sections")
@NoArgsConstructor @AllArgsConstructor @Builder
public class Section extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private SectionName name;
    @Column(unique = true)
    private String roomNo;
    private Integer  capacity;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectAssignment> subjectAssignments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolClass schoolClass;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SectionTeacherAssignment> teacherAssignments = new HashSet<>();

    @OneToMany(mappedBy = "section")
    private Set<Student> students = new HashSet<>();

    public Set<SectionTeacherAssignment> getTeacherAssignments() {
        return teacherAssignments;
    }

    public void setTeacherAssignments(Set<SectionTeacherAssignment> teacherAssignments) {
        this.teacherAssignments = teacherAssignments;
    }

    public Set<SubjectAssignment> getSubjectAssignments() {
        return subjectAssignments;
    }

    public void setSubjectAssignments(Set<SubjectAssignment> subjectAssignments) {
        this.subjectAssignments = subjectAssignments;
    }

    public SectionName getName() {
        return name;
    }

    public void setName(SectionName name) {
        this.name = name;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}