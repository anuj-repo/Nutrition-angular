package com.fertilizer.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SecondRoundRequest {

	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	private String name;

	@Size(min = 5, max = 100, message = "Address must be between 5 and 100 characters")
	private String address;

	@Email(message = "Email should be valid")
	private String email;

	@Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
	private String phoneNumber;

	private String isSelected;

}
