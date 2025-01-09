package com.fertilizer.request1;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Dhiraj
 *
 */
@Getter
@Setter
public class ResetPasswordDto {

	@NotBlank(message = "{validation.token.NotBlank}")
	private String token;

	@NotBlank(message = "{validation.password.NotBlank}")
	@Pattern(regexp = "^(?=.{6,40})(?=.*[a-z])(?=.*[A-Z])(?=.*?[0-9])(?=.*[*@#$%^&?!_-]).*$", message = "{validation.password.PatternMessage}")
	private String password;
	
	@NotBlank(message = "{validation.confirmpassword.NotBlank}")
	@Pattern(regexp = "^(?=.{6,40})(?=.*[a-z])(?=.*[A-Z])(?=.*?[0-9])(?=.*[*@#$%^&?!_-]).*$", message = "{validation.password.PatternMessage}")
	private String confirmPassword;

}
