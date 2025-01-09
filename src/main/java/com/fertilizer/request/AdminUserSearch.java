package com.fertilizer.request;

import com.fertilizer.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserSearch {

	private String startDate;
	private String endDate;
	private String specificDate;
	private String referralCode;
	private String mobileNumber;
	private String status;

}
