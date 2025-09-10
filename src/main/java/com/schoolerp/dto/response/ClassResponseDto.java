package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.enums.ClassStandard;

public class ClassResponseDto extends BaseDTO {
    private ClassStandard name;
    public ClassStandard getName() {
        return name;
    }

    public void setName(ClassStandard name) {
        this.name = name;
    }

}