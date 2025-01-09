package com.fertilizer.request1;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Dhiraj
 *
 */
public class ActivatePasswordDTO {
	@NotBlank(message = "{validation.token.NotBlank}")
	private String token;

	@NotBlank(message = "{validation.password.NotBlank}")
	@Pattern(regexp = "^(?=.{6,40})(?=.*[a-z])(?=.*[A-Z])(?=.*?[0-9])(?=.*[*@#$%^&?!_-]).*$", message = "{validation.password.PatternMessage}")
	private String password;
	
	//@NotBlank
	//@Size(min = 10, max = 20, message = "Invalid Phone Number")
	//@Pattern(regexp = "\\d{10,20}", message = "Invalid Phone Number")
	private String phone;
	
	//@NotBlank
	//@Pattern(regexp = "\\d+")
	private String countryStdCode;
	
	private String firstName;
	
	private String lastName;
	
	private String username;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountryStdCode() {
		return countryStdCode;
	}

	public void setCountryStdCode(String countryStdCode) {
		this.countryStdCode = countryStdCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
