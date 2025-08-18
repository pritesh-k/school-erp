package com.schoolerp.dto.request;

import jakarta.validation.constraints.NotBlank;

public class StudentEnrollmentUpdateDTO {

    @NotBlank
    private Long id;
    private Long schoolClassId;
    private Long sectionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
