package com.fertilizer.annotation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fertilizer.annotation.ValidUserByUsername;
import com.fertilizer.enums.Status;
import com.fertilizer.repository.UserRepository;


public class ValidUserByUsernameValidator implements ConstraintValidator<ValidUserByUsername, String> {
	private static final Logger logger = LogManager.getLogger(ValidUserByUsernameValidator.class);

	@Autowired
	private UserRepository expressionRepository;

	@Override
	@Transactional(readOnly = true)
	public boolean isValid(String username, ConstraintValidatorContext context) {

		logger.debug(String.format("ValidUserByUsernameValidator Occured with value %S  ", username));

		if (username != null)
			return expressionRepository.existsByUsernameAndStatus(username, Status.ACTIVE);

		return false;
	}
}