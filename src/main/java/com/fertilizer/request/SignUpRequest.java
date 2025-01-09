package com.fertilizer.request;

import com.fertilizer.enums.GenderEnum;
import com.fertilizer.enums.PackageChoiceEnum;
import com.fertilizer.enums.PackageTakenEnum;

import lombok.Data;

@Data
public class SignUpRequest {

	private String firstName;

	private String lastName;

	private String referralCode;

	private String email;

	private String mobNumber;

	private String password;

	private String confirmPassword;;

	private String dob;

	private GenderEnum gender;

	private String address;

	private Long city;

	private Long state;

	private String zipCode;

	// private String upiId;

	private PackageTakenEnum packageTaken;

	private PackageChoiceEnum productChoice;

	// private String uploadPhoto;

}
