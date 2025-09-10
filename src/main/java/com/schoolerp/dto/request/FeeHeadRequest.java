package com.schoolerp.dto.request;

import com.schoolerp.enums.FeeCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeHeadRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;
    private String description;

    @NotNull
    private FeeCategory category;
    private Boolean isMandatory = true;

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
