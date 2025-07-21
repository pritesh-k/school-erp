package com.schoolerp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "teachers")
@NoArgsConstructor @AllArgsConstructor @Builder
public class Teacher extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(unique = true)
    private String employeeCode;
    private String firstName;
    private String lastName;
    private String displayName;

    public void createFullName() {
        this.displayName = ((firstName != null ? firstName : "") +
                " " + (lastName != null ? lastName : "")).trim();
    }
    private String phone;
    private String email;
    private LocalDate joiningDate;

    private Long classTeacherOfSection;

    @ManyToMany(mappedBy = "assignedTeachers")
    private Set<Section> assignedSections = new HashSet<>();

    @ManyToMany(mappedBy = "teachersAssigned")
    private Set<Subject> subjects = new HashSet<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Long getClassTeacherOfSection() {
        return classTeacherOfSection;
    }

    public void setClassTeacherOfSection(Long classTeacherOfSection) {
        this.classTeacherOfSection = classTeacherOfSection;
    }

    public Set<Section> getAssignedSections() {
        return assignedSections;
    }

    public void setAssignedSections(Set<Section> assignedSections) {
        this.assignedSections = assignedSections;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher other = (Teacher) o;
        return this.getId() != null && this.getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}