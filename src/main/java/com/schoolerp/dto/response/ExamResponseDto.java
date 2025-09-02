package com.schoolerp.dto.response;
import com.schoolerp.dto.BaseDTO;
import com.schoolerp.entity.SchoolClass;
import com.schoolerp.entity.ExamResult;
import com.schoolerp.entity.Subject;
import com.schoolerp.enums.ExamStatus;
import com.schoolerp.enums.Term;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ExamResponseDto extends BaseDTO {
    private String name;
    private Term term;
    private LocalDate startDate;
    private LocalDate endDate;

    private ExamStatus status;

    public ExamStatus getStatus() {
        return status;
    }

    public void setStatus(ExamStatus status) {
        this.status = status;
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