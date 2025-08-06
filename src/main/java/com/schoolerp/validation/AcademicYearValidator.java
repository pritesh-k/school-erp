package com.schoolerp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AcademicYearValidator implements ConstraintValidator<ValidAcademicYear, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || !value.matches("^(19|20)\\d{2}-(19|20)\\d{2}$")) {
            return false;
        }

        String[] years = value.split("-");
        int startYear = Integer.parseInt(years[0]);
        int endYear = Integer.parseInt(years[1]);

        return endYear == startYear + 1;
    }
}

