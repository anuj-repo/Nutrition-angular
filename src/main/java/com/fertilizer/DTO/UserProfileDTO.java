package com.fertilizer.DTO;

import com.fertilizer.model.User;

import lombok.Data;

@Data
public class UserProfileDTO {
	private String fname;
	private String lname;
	private String email;
	private String mobileNumber;
	private String address;
	private String city;
	private String state;
	private String country;
	private String pincode;

	public UserProfileDTO(User user) {
		this.fname = user.getFname();
		this.lname = user.getLname();
		this.email = user.getEmail();
		this.mobileNumber = user.getMobileNumber();
		this.address = user.getAddress();
		this.city = user.getCity();
		this.state = user.getState();
		this.country = user.getCountry();
		this.pincode = user.getPincode();
	}

}
