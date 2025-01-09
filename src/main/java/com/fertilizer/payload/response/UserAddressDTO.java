package com.fertilizer.payload.response;

import com.fertilizer.enums.AddressType;
import com.fertilizer.enums.BooleanEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class UserAddressDTO {
	private Long id;
	
	private String address;

	private String state;

	private String country;

	private String pincode;

	private String city;
	
	private BooleanEnum  isDefault ;
	
	private AddressType addressType;
	
	private BooleanEnum isOtherCity;
}
