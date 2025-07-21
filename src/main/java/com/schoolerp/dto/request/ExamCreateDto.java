package com.schoolerp.dto.request;
import com.schoolerp.enums.Term;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;


public class ExamCreateDto{
    @NotBlank String name;
    @NotNull Term term;
    @NotNull LocalDate startDate;
    @NotNull LocalDate endDate;
    @NotNull Long schoolClassId;
    @NotEmpty Set<@NotNull Long> subjectIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(Long schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    public Set<Long> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(Set<Long> subjectIds) {
        this.subjectIds = subjectIds;
    }
}