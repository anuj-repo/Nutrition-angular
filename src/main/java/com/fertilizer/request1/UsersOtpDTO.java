package com.fertilizer.request1;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fertilizer.enums.OtpFor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersOtpDTO {

	@NotNull(message = "Otp For is required")
	OtpFor otpFor;
	
	@Email(message = "{validation.email.pattern}")
	String  email;
	
	@Pattern(regexp = "\\d{10,20}", message = "{validation.phone.Pattern}")
	String phone;
}
