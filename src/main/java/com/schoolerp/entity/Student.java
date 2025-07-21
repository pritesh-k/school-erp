package com.schoolerp.entity;

import com.schoolerp.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@NoArgsConstructor @AllArgsConstructor @Builder
public class Student extends BaseEntity {
    @Column(unique = true)
    private String admissionNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String email;
    private String rollNumber;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate dob;
    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY)
    private Section section;

    @ManyToMany
    @JoinTable(name = "student_parent",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id"))
    private Set<Parent> parents = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<Attendance> attendances = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<ExamResult> examResults = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<FeeRecord> feeRecords = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<Document> documents = new HashSet<>();

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
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

    public Set<Parent> getParents() {
        return parents;
    }

    public void setParents(Set<Parent> parents) {
        this.parents = parents;
    }

    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    public Set<ExamResult> getExamResults() {
        return examResults;
    }

    public void setExamResults(Set<ExamResult> examResults) {
        this.examResults = examResults;
    }

    public Set<FeeRecord> getFeeRecords() {
        return feeRecords;
    }

    public void setFeeRecords(Set<FeeRecord> feeRecords) {
        this.feeRecords = feeRecords;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}