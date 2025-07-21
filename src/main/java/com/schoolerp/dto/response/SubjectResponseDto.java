package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.entity.SchoolClass;
import com.schoolerp.entity.Teacher;
import com.schoolerp.enums.SubjectCategory;
import com.schoolerp.enums.SubjectCode;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class SubjectResponseDto extends BaseDTO{
    private SubjectCode code;
    private SubjectCategory category;

    public SubjectCategory getCategory() {
        return category;
    }

    public void setCategory(SubjectCategory category) {
        this.category = category;
    }

    public SubjectCode getCode() {
        return code;
    }

    public void setCode(SubjectCode code) {
        this.code = code;
    }
}

