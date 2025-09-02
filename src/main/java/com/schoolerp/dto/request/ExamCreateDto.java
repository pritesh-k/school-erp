package com.schoolerp.dto.request;
import com.schoolerp.enums.Term;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;


public class ExamCreateDto{
    @NotBlank private String name;
    @NotNull private Term term;
    @NotNull private LocalDate startDate;
    @NotNull private LocalDate endDate;
    @AssertTrue(message = "End date must not be before start date")
    public boolean isValidDates() {
        return startDate != null && endDate != null && !endDate.isBefore(startDate);
    }
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
}