package com.schoolerp.dto.request;
import com.schoolerp.enums.Relation;
import jakarta.validation.constraints.*;
public record ParentCreateDto(
        @NotBlank String username,
        @Email String email,
        @NotBlank String password,
        @NotBlank String firstName,
        String lastName,
        @NotBlank String phone,
        String occupation,
        @NotNull Relation relation
) {}