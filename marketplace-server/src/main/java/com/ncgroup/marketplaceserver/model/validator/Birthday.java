package com.ncgroup.marketplaceserver.model.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;

@NotNull
@Documented
@Constraint(validatedBy = BirthdayValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Birthday {
	String message() default "User must be at least 18 y.o.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
