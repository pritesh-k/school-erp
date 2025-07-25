package com.schoolerp.dto.request;

import com.schoolerp.enums.ClassStandard;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ClassCreateDto {
        @NotBlank ClassStandard name;

        public ClassStandard getName() {
                return name;
        }

        public void setName(ClassStandard name) {
                this.name = name;
        }

}