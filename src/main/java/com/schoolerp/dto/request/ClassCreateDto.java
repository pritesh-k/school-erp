package com.schoolerp.dto.request;

import com.schoolerp.enums.ClassStandard;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ClassCreateDto {
        @NotBlank ClassStandard name;
        List<SectionCreateDto> sections;

        public ClassStandard getName() {
                return name;
        }

        public void setName(ClassStandard name) {
                this.name = name;
        }

        public List<SectionCreateDto> getSections() {
                return sections;
        }

        public void setSections(List<SectionCreateDto> sections) {
                this.sections = sections;
        }
}