package com.fertilizer.DTO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.fertilizer.enums.Status;
import com.fertilizer.model.User;

import lombok.Data;

@Data
public class UserHierarchyDTO {
	
	private Long userId;
	
	private String fname;

	private String lname;

	private String email;

	private String mobileNumber;

	private BigDecimal totalAccountBalance;

	private BigDecimal paidBalance;

	private BigDecimal accountBalance;

	private String referralCode;
	
	private Status status;

	private List<UserHierarchyDTO> children;

	public UserHierarchyDTO(User user) {
		this.userId=user.getId();
		this.fname = user.getFname();
		this.lname = user.getLname();
		this.email = user.getEmail();
		this.mobileNumber = user.getMobileNumber();
		this.totalAccountBalance = user.getTotalAccountBalance();
		this.paidBalance = user.getPaidBalance();
		this.accountBalance = user.getAccountBalance();
		this.referralCode = user.getReferralCode();
		this.status=user.getStatus();
		this.children = new ArrayList<>();
		
	}

//	public List<UserHierarchyDTO> getChildren() {
//		return children;
//	}
//
//	public void setChildren(List<UserHierarchyDTO> children) {
//		this.children = children;
//	}
}
