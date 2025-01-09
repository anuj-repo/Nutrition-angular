package com.fertilizer.annotation.validator;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fertilizer.annotation.IsFutureDate;
import com.fertilizer.util.DateUtil;

public class IsFutureDateValidator implements ConstraintValidator<IsFutureDate, String> {

	private static final Logger logger = LogManager.getLogger(IsFutureDateValidator.class);

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		logger.debug(String.format("ValidEventByEventCodeValidator Occured with value %S  ", value));

		if (value != null) {

			return DateUtil.getLocalDateFromString(value).isAfter(DateUtil.getLocalDateByDate(new Date()));
		}

		return true;
	}

}
