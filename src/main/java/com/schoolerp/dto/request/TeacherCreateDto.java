package com.schoolerp.dto.request;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public record TeacherCreateDto(
        @NotBlank String username,
        @Email String email,
        @NotBlank String password,
        @NotBlank String employeeCode,
        @NotBlank String firstName,
        String lastName,
        @NotBlank String phone,
        @NotBlank LocalDate joiningDate
) {}