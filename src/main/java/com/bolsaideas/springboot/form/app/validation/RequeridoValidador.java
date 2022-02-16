package com.bolsaideas.springboot.form.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//helper
import org.springframework.util.StringUtils;

public class RequeridoValidador implements ConstraintValidator<Requerido, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || !StringUtils.hasText(value)) {
			return false;
		}
		// Varias formas de hacerlo
//		if (value == null || value.isEmpty() || value.isBlank()) {
//			return false;
//		}
		return true;
	}

}
