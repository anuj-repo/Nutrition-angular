package com.fertilizer.request1;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginRequest {
	
	@NotBlank
	@NotNull
	private String usernameOrEmail;

	@NotBlank
	@NotNull
	private String password;

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginRequest(@NotBlank String usernameOrEmail, @NotBlank String password) {
		super();
		this.usernameOrEmail = usernameOrEmail;
		this.password = password;
	}

	public LoginRequest() {
		super();
	}

	@Override
	public String toString() {
		return "LoginRequest [usernameOrEmail=" + usernameOrEmail + ", password=" + password + "]";
	}
	
	

}
