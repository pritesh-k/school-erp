package com.schoolerp.dto.request;

import com.schoolerp.enums.FeeCategory;

public class FeeHeadUpdateRequest {
    private String name;
    private String description;
    private FeeCategory category;
    private Boolean isMandatory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FeeCategory getCategory() {
        return category;
    }

    public void setCategory(FeeCategory category) {
        this.category = category;
    }

    public Boolean getMandatory() {
        return isMandatory;
    }

    public void setMandatory(Boolean mandatory) {
        isMandatory = mandatory;
    }
}
