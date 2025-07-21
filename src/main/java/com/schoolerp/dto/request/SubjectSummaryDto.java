package com.schoolerp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectSummaryDto {
    private Long id;
    private String code; // SubjectCode enum as string
    private String category; // SubjectCategory enum as string
    private List<String> assignedClasses; // List of class names where subject is taught

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getAssignedClasses() {
        return assignedClasses;
    }

    public void setAssignedClasses(List<String> assignedClasses) {
        this.assignedClasses = assignedClasses;
    }
}
