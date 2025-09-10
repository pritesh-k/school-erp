package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeStructureResponse extends BaseDTO {
    private ClassResponseDto schoolClass;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClassResponseDto getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(ClassResponseDto schoolClass) {
        this.schoolClass = schoolClass;
    }
}