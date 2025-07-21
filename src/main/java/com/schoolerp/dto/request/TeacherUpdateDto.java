package com.schoolerp.dto.request;

import jakarta.validation.constraints.Email;

import java.time.LocalDate;

public record TeacherUpdateDto(
        @Email String email,
        String password,
        String employeeCode,
        String firstName,
        String lastName,
        String phone,
        LocalDate joiningDate
) {}