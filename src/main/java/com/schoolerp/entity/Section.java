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

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolClass schoolClass;

    //Store ClassTeacher Id only and add the real teacher to teacher table, and we can get that from below column assignedTeachers
    @Column(name = "class_teacher_id")
    private Long classTeacherId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "section_teacher",
            joinColumns = @JoinColumn(name = "section_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private Set<Teacher> assignedTeachers = new HashSet<>();

    @OneToMany(mappedBy = "section")
    private Set<Student> students = new HashSet<>();

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

    public Long getClassTeacherId() {
        return classTeacherId;
    }

    public void setClassTeacherId(Long classTeacherId) {
        this.classTeacherId = classTeacherId;
    }

    public Set<Teacher> getAssignedTeachers() {
        return assignedTeachers;
    }

    public void setAssignedTeachers(Set<Teacher> assignedTeachers) {
        this.assignedTeachers = assignedTeachers;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}