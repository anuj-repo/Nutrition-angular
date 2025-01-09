package com.fertilizer.request1;

import com.fertilizer.enums.UserType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisteredUsersListRequestDTO extends CommonRequestDTO {

	private String status;

	private UserType userType;

	private String city;

	public RegisteredUsersListRequestDTO(String status, UserType userType, String city, String smartSearch,
			String sortBy, String sortDir, Long page, Long size) {
		super(smartSearch, sortBy, sortDir, page, size);
		this.status = status;
		this.city = city;
		this.userType = userType;
	}
}
