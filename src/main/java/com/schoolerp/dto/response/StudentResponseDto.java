package com.schoolerp.dto.response;
import com.schoolerp.dto.BaseDTO;
import com.schoolerp.entity.*;
import com.schoolerp.entity.SchoolClass;
import com.schoolerp.enums.Gender;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class StudentResponseDto extends BaseDTO {
    private String admissionNumber;
    private String email;
    private String rollNumber;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dob;
    private SchoolClass aSchoolClass;
    private Section section;
    private Set<Parent> parents = new HashSet<>();

    private Set<Attendance> attendances = new HashSet<>();
    private Set<ExamResult> examResults = new HashSet<>();
    private Set<FeeRecord> feeRecords = new HashSet<>();
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
        return aSchoolClass;
    }

    public void setSchoolClass(SchoolClass aSchoolClass) {
        this.aSchoolClass = aSchoolClass;
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
}