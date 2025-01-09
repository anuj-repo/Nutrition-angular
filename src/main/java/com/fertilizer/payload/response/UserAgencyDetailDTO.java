package com.fertilizer.payload.response;

import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.CompanyTurnOver;
import com.fertilizer.enums.UserType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAgencyDetailDTO {
	private Long id;

	private String email;

	private String contact;

	private String companyName;

	private String address;
	
	private String fname;

	private String lname;
	
	private String designation;

	private String compName;
	
	private Integer businessCategory;
	
	private String businessCategoryOther;
	
	private BooleanEnum hasAdvertisedBefore;
	
	private String advertisedIn;
	
	private String advertisedOtherIn;
	
	private CompanyTurnOver companyTurnOver;
	
	private String gstNumber;
	
	private String panNumber;
	
	private String city;
	
	private String state;
	
	private String pincode;
	
	private UserType userType;
	
}
