package com.schoolerp.dto.request;
import com.schoolerp.enums.SubjectCategory;
import com.schoolerp.enums.SubjectCode;
import jakarta.validation.constraints.*;
public record SubjectCreateDto(@NotBlank SubjectCode code, @NotBlank SubjectCategory category) {}