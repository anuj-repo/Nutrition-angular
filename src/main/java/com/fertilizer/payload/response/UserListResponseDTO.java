package com.fertilizer.payload.response;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.UserType;
import com.fertilizer.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListResponseDTO {

	private Long userId;

	private String fname;

	private String lname;

	private Long ownerId;

	private String userEmail;

	private Role role;

	private String city;

	private String userPhone;

	private String userLogo;
	
	private UserType userType;
	
	private Date createdAt;
	
	private String companyName;

	@JsonSerialize(using = ToStringSerializer.class)
	private BooleanEnum isPremiumPrintCustomer;

	@JsonSerialize(using = ToStringSerializer.class)
	private BooleanEnum isAccreditedCustomer;

	@JsonSerialize(using = ToStringSerializer.class)
	private BooleanEnum isPdcEnabled;

	@JsonSerialize(using = ToStringSerializer.class)
	private BooleanEnum isPremiumRadioCustomer;

	public UserListResponseDTO(Long userId,String fname, String lname, Long ownerId,
			String userEmail, Role role, String city, String userPhone, String userLogo,UserType userType,Date createdAt,String companyName, BooleanEnum isPremiumPrintCustomer,
							   BooleanEnum isAccreditedCustomer,BooleanEnum isPdcEnabled, BooleanEnum isPremiumRadioCustomer) {
		super();
		this.userId = userId;
		this.fname = fname;
		this.lname = lname;
		this.ownerId = ownerId;
		this.userEmail = userEmail;
		this.role = role;
		this.city = city;
		this.userPhone = userPhone;
		this.userLogo = userLogo;
		this.userType=userType;
		this.createdAt=createdAt;
		this.companyName=companyName;
		this.isPremiumPrintCustomer = isPremiumPrintCustomer;
		this.isAccreditedCustomer = isAccreditedCustomer;
		this.isPdcEnabled = isPdcEnabled;
		this.isPremiumRadioCustomer = isPremiumRadioCustomer;
	}

}
