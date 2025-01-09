package com.fertilizer.request1;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.Channel;
import com.fertilizer.enums.UserType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {

	@NotBlank(message = "{validation.fname.NotBlank}")
	@Size(max = 50, message = "{validation.fname.Size}")
	private String fname;

	@NotBlank(message = "{validation.lname.NotBlank}")
	@Size(max = 50, message = "{validation.lname.Size}")
	private String lname;

	@Email(message = "{validation.email.pattern}")
	@NotBlank(message = "{validation.email.NotBlank}")
	private String email;

	@Pattern(regexp = "\\d{10,20}", message = "{validation.phone.Pattern}")
	@NotBlank(message = "{validation.phone.NotBlank}")
	private String phone1;

	@NotBlank(message = "{validation.companyName.NotBlank}")
	@Size(max = 80, message = "{validation.companyName.Size}")
	private String compName;
	
	@NotNull(message = "{validation.UserAgency.NotBlank}")
	private UserType userType;
	
	@NotBlank(message = "{validation.password.NotBlank}")
	@Pattern(regexp = "^(?=.{6,40})(?=.*[a-z])(?=.*[A-Z])(?=.*?[0-9])(?=.*[*@#$%^&?!_-]).*$", message = "{validation.password.PatternMessage}")
//	  ^[^-\\s](?=.*?[A-Za-z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-_]).{6,40}$
	private String password;
	
	@NotBlank(message = "{validation.confirmpassword.NotBlank}")
	@Pattern(regexp = "^(?=.{6,40})(?=.*[a-z])(?=.*[A-Z])(?=.*?[0-9])(?=.*[*@#$%^&?!_-]).*$", message = "{validation.password.PatternMessage}")
	private String confirmPassword;
	
	@NotBlank(message = "{validation.state.NotBlank}")
	@Pattern(regexp = "^[ a-zA-Z]*$", message = "{validation.state.Pattern}")
	private String state;
	
	@NotBlank(message = "{validation.city.NotBlank}")
	@Pattern(regexp = "^[ a-zA-Z]*$", message = "{validation.city.Pattern}")
	private String city;
	
	@NotBlank(message = "{validation.gst.NotBlank}")
	private String gstNumber;
	
	private Channel addType=Channel.PRINT;

	private String captcha;
}