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
@Table(name = "teachers",
        indexes = {
                @Index(name = "idx_teacher_user_id", columnList = "user_id"),
                @Index(name = "idx_teacher_email", columnList = "email"),
})
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
    private String phone;
    private String email;
    private LocalDate joiningDate;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SectionTeacherAssignment> sectionAssignments = new HashSet<>();

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectAssignment> subjectAssignments = new HashSet<>();

    public void createFullName() {
        this.displayName = ((firstName != null ? firstName : "") +
                " " + (lastName != null ? lastName : "")).trim();
    }

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

    public Set<SectionTeacherAssignment> getSectionAssignments() {
        return sectionAssignments;
    }

    public void setSectionAssignments(Set<SectionTeacherAssignment> sectionAssignments) {
        this.sectionAssignments = sectionAssignments;
    }

    public Set<SubjectAssignment> getSubjectAssignments() {
        return subjectAssignments;
    }

    public void setSubjectAssignments(Set<SubjectAssignment> subjectAssignments) {
        this.subjectAssignments = subjectAssignments;
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