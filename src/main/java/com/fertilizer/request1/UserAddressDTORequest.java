package com.fertilizer.request1;


import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fertilizer.annotation.BlankOrPattern;
import com.fertilizer.enums.AddressType;
import com.fertilizer.enums.BooleanEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressDTORequest {
	
	public UserAddressDTORequest() {
	}

	private Long id;
	
	@Length(max=255,message= "{validation.address.Size}")	
	private String address;

	@Pattern(regexp = "^[ a-zA-Z]*$", message = "{validation.state.Pattern}")
	private String state;

	@BlankOrPattern(regexp = "^[0-9]{6}$", message = "{validation.pin.Pattern}")
	private String pincode;
	
	@Pattern(regexp = "^[ a-zA-Z]*$", message = "{validation.city.Pattern}")
	private String city;
	
	private BooleanEnum  isDefault ;
	
	private AddressType addressType;
	

	public UserAddressDTORequest(Long id,String address,
			String state, String pincode,
			String city, BooleanEnum isDefault,
			AddressType addressType) {
		this.id = id;
		this.address = address;
		this.state = state;
		this.pincode = pincode;
		this.city = city;
		this.isDefault = isDefault;
		this.addressType = addressType;
	}
	
}
