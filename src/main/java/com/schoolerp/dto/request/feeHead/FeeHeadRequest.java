package com.schoolerp.dto.request.feeHead;

import jakarta.validation.constraints.NotBlank;

public class FeeHeadRequest {
    @NotBlank
    private String name; // e.g. "Tuition", "Transport", "Library"

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
