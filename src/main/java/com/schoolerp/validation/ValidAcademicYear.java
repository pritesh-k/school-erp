package com.schoolerp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AcademicYearValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAcademicYear {
    String message() default "Academic year must be in format YYYY-YYYY and the second year must be first year + 1";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

