package com.schoolerp.dto.request;
import com.schoolerp.entity.User;
import com.schoolerp.enums.Gender;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public class StudentCreateDto {
    @NotBlank @NotNull private String admissionNumber;
    @NotBlank @NotNull private String rollNumber;
    @NotBlank @NotNull private String firstName;
    private String lastName;
    @NotNull @NotNull private LocalDate dob;
    private Long schoolClassId;
    private Long sectionId;

    private Gender gender;

    @Email
    private String email;

    private ParentCreateDto parentCreateDto;

    public ParentCreateDto getParentCreateDto() {
        return parentCreateDto;
    }

    public void setParentCreateDto(ParentCreateDto parentCreateDto) {
        this.parentCreateDto = parentCreateDto;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }


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


    public Long getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(Long schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}