package com.ncgroup.marketplaceserver.model.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BirthdayValidator implements ConstraintValidator<Birthday, LocalDate> {

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		return value.isBefore(LocalDate.now().minusYears(18).plusDays(1));
	}

}
