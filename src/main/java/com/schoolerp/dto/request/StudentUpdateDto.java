package com.schoolerp.dto.request;

import com.schoolerp.enums.Gender;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;

public class StudentUpdateDto {
    private String admissionNumber;
    private String rollNumber;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private Gender gender;
    @Email
    private String email;
    private ParentUpdateDto parent;

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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ParentUpdateDto getParent() {
        return parent;
    }

    public void setParent(ParentUpdateDto parent) {
        this.parent = parent;
    }
}
