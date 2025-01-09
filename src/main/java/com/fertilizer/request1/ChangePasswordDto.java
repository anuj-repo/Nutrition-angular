package com.fertilizer.request1;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Dhiraj
 *
 */
public class ChangePasswordDto {

	@NotBlank(message = "{validation.oldPassword.NotBlank}")
	private String oldPassword;

	@NotBlank(message = "{validation.newPassword.NotBlank}")
	@Pattern(regexp = "^(?=.{6,40})(?=.*[a-z])(?=.*[A-Z])(?=.*?[0-9])(?=.*[*@#$%^&?!_-]).*$", message = "{validation.password.PatternMessage}")
//	 ^[^-\\s](?=.*?[A-Za-z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-_]).{6,40}$
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
