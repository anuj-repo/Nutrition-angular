package com.fertilizer.payload.response;

import java.util.Date;

import com.fertilizer.enums.ApprovalStatus;
import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientListResponseDTO {

	private Long companyId;
	private String companyName;

	private String agencyId;
	private Status status;
	private String fname;
	private String lname;
	private String email;
	private String phone1;
	private String address;
	private String state;
	private String city;
	private String pincode;
	private Integer businessCategory;
	private String businessCategoryOther;
	private String gstNumber;
	private String panNumber;
	private BooleanEnum isUserForDigital;
	private BooleanEnum isUserForRadio;
	private BooleanEnum isUserForPrint;
	private ApprovalStatus approvedForDigital;
	private ApprovalStatus approvedForRadio;
	private ApprovalStatus approvedForPrint;
	private Date createdAt;

	public ClientListResponseDTO(Long companyId, String companyName,String agencyId, Status status, String fname, String lname,
			String email, String phone1, String address, String state, String city, String pincode,
			Integer businessCategory, String businessCategoryOther, String gstNumber, String panNumber,
			BooleanEnum isClientForDigital, BooleanEnum isClientForRadio, BooleanEnum isClientForPrint,
			ApprovalStatus approvedForDigital, ApprovalStatus approvedForRadio, ApprovalStatus approvedForPrint, Date createdAt) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.agencyId = agencyId;
		this.status = status;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.phone1 = phone1;
		this.address = address;
		this.state = state;
		this.city = city;
		this.pincode = pincode;
		this.businessCategory = businessCategory;
		this.businessCategoryOther = businessCategoryOther;
		this.gstNumber = gstNumber;
		this.panNumber = panNumber;
		this.isUserForDigital = isClientForDigital;
		this.isUserForRadio = isClientForRadio;
		this.isUserForPrint = isClientForPrint;
		this.approvedForDigital = approvedForDigital;
		this.approvedForRadio = approvedForRadio;
		this.approvedForPrint = approvedForPrint;
		this.createdAt = createdAt;
	}
	
	public ClientListResponseDTO(Long companyId, String companyName) {
		this.companyId = companyId;
		this.companyName = companyName;
	}

}
