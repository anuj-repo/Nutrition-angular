package com.fertilizer.payload.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.UserType;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsDTO {
	@JsonIgnore
	private Long id;
	
	private String fname;
	
	private String lname;

	private String email;

	private String contact;
	
	private String companyName;

	private String address;
	
	private Integer voucherCatId;
	
	private Double totalVoucherPurchased;
	
	private Double totalAdvancePaid;
	
	
	
	private Double totalAmountSpent;
	
	private Long totalAdvanceUsed;
	
	private Long loyaltyPoint;
	
	private Double totalRemainingBalance;

	
	private String gstNumber;
	
	private String panNumber;
	
	private String city;
	
	private String state;
	
	private String zipCode;
	
	private UserType userType;
	
	private BooleanEnum isDetailCompleted;

	public UserDetailsDTO(Long id, String fname, String lname, String email, String contact,
			String companyName, String address,Integer voucherCatId, Double totalVoucherPurchased, Double totalAdvancePaid,
			 Double totalAmountSpent, Long totalAdvanceUsed, Long loyaltyPoint,UserType userType,BooleanEnum isDetailCompleted) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.contact = contact;
		this.companyName = companyName;
		this.address = address;
		this.voucherCatId = voucherCatId;
		this.totalVoucherPurchased = totalVoucherPurchased;
		this.totalAdvancePaid = totalAdvancePaid;
		this.totalAmountSpent = totalAmountSpent;
		this.totalAdvanceUsed = totalAdvanceUsed;
		this.loyaltyPoint = loyaltyPoint;
		this.userType = userType;
		this.isDetailCompleted = isDetailCompleted;
	}
	
	public UserDetailsDTO(String fname, String lname, String email, String contact,
			String companyName, String address,String gstNumber,String panNumber,String city,String state,
			String zipCode,UserType userType,Double totalVoucherPurchased, Double totalAdvancePaid,BooleanEnum isDetailCompleted) {
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.contact = contact;
		this.companyName = companyName;
		this.address = address;
		this.gstNumber = gstNumber;
		this.panNumber = panNumber;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.totalVoucherPurchased = totalVoucherPurchased;
		this.totalAdvancePaid = totalAdvancePaid;
		this.userType = userType;
		this.isDetailCompleted = isDetailCompleted;
	}
	
	public UserDetailsDTO(String fname, String lname, String email, String contact,
			String companyName, String address,String gstNumber,String panNumber,String city,String state,
			String zipCode, UserType userType,BooleanEnum isDetailCompleted) {
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.contact = contact;
		this.companyName = companyName;
		this.address = address;
		this.gstNumber = gstNumber;
		this.panNumber = panNumber;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.userType = userType;
		this.isDetailCompleted = isDetailCompleted;
	}

	public UserDetailsDTO() {
	}

}