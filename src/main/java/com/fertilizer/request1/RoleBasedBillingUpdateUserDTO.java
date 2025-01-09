package com.fertilizer.request1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleBasedBillingUpdateUserDTO {

	private String firstName;

	private String lastName;

	private String phoneNumber;

	private String address;

	private Long addressId;

	private String pincode;

	private String city;

	private String state;

}