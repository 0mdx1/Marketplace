package com.ncgroup.marketplaceserver.model.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Size(min = 6, max = 32)
@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,12}$")
@Documented
@Constraint(validatedBy = {})
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
	String message() default "Password must have between 6 and 32 symbols and contail at least one digit, at least one "
			+ "capital letter and at least one lowercase letter";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
