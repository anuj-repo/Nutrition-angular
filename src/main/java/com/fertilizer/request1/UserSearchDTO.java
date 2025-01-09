package com.fertilizer.request1;


import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.UserType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchDTO extends CommonSearchDto {

	private String userEmail;

	private String status;
	
	private UserType userType;
	
	private String city;

	private BooleanEnum isPremiumPrintCustomer;

	private BooleanEnum isAccreditedCustomer;

	private BooleanEnum isPremiumRadioCustomer;

	private BooleanEnum isPremiumDigitalCustomer;

	public UserSearchDTO(RegisteredUsersListRequestDTO registeredUsersListRequestDto) {
		super(registeredUsersListRequestDto.getSmartSearch(), registeredUsersListRequestDto.getPage(),
				registeredUsersListRequestDto.getSize(), registeredUsersListRequestDto.getSortBy(),
				registeredUsersListRequestDto.getSortDir());
		this.status = registeredUsersListRequestDto.getStatus();
		this.city=registeredUsersListRequestDto.getCity();
		this.userType=registeredUsersListRequestDto.getUserType();
	}

}
