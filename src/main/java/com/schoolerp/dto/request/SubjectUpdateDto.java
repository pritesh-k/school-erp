package com.schoolerp.dto.request;

import com.schoolerp.enums.SubjectCategory;
import com.schoolerp.enums.SubjectCode;

public record SubjectUpdateDto(SubjectCode code, SubjectCategory category) { }
