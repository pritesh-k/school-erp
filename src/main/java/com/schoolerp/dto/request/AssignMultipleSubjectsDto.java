package com.schoolerp.dto.request;

import java.util.List;

public class AssignMultipleSubjectsDto {
    private List<Long> subjectIds;

    public List<Long> getSubjectIds() { return subjectIds; }
    public void setSubjectIds(List<Long> subjectIds) { this.subjectIds = subjectIds; }
}