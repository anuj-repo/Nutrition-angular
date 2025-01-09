package com.fertilizer.request1;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Dhiraj
 *
 */
@Getter
@Setter
public class UserOtpDTO {
	
	@NotBlank(message = "{validation.email.NotBlank}")
	@Email(message = "{validation.email.pattern}")
	private String email;
	
	@NotBlank(message = "{validation.otp.NotBlank}")
	private String otp;
}
