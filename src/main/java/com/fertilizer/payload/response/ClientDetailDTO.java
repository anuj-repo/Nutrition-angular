package com.fertilizer.payload.response;

import com.fertilizer.enums.ApprovalStatus;
import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.ClientType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDetailDTO {
	private String fname;

	private String lname;
	
	private String compName;

	private Integer businessCategory;

	private String businessCategoryOther;
	
	private String email;

	private String phone1;

	private String address;
	
	private String state;
	
	private String city;
	
	private String pincode;
	
	private String gstNumber; 
	
	private String panNumber;
	
	private String advertisedIn;
	
	private BooleanEnum isClientForDigital;
	
	private BooleanEnum isClientForRadio;
	
	private BooleanEnum isClientForPrint;
	
	private ApprovalStatus approvedForDigital;
	
	private ApprovalStatus approvedForRadio;
	
	private ApprovalStatus  approvedForPrint;
	
	private Long radioStatusChangedBy;
	
	private Long digitalStatusChagedBy;
	
	private Long printStatusChangedBy;
	
	private ClientType clientType;
	
	private String sfdcIdForPrint;
	
	private String sfdcIdForRadio;

	private Long userId;
	
	public ClientDetailDTO(String fname, String lname, String compName, Integer businessCategory,
			String businessCategoryOther, String email, String phone1, String address, String state, String city,
			String pincode, String gstNumber, String panNumber, String advertisedIn, BooleanEnum isClientForDigital,
			BooleanEnum isClientForRadio, BooleanEnum isClientForPrint, ApprovalStatus approvedForDigital,
			ApprovalStatus approvedForRadio, ApprovalStatus approvedForPrint, Long radioStatusChangedBy,
			Long digitalStatusChagedBy, Long printStatusChangedBy, ClientType clientType) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.compName = compName;
		this.businessCategory = businessCategory;
		this.businessCategoryOther = businessCategoryOther;
		this.email = email;
		this.phone1 = phone1;
		this.address = address;
		this.state = state;
		this.city = city;
		this.pincode = pincode;
		this.gstNumber = gstNumber;
		this.panNumber = panNumber;
		this.advertisedIn = advertisedIn;
		this.isClientForDigital = isClientForDigital;
		this.isClientForRadio = isClientForRadio;
		this.isClientForPrint = isClientForPrint;
		this.approvedForDigital = approvedForDigital;
		this.approvedForRadio = approvedForRadio;
		this.approvedForPrint = approvedForPrint;
		this.radioStatusChangedBy = radioStatusChangedBy;
		this.digitalStatusChagedBy = digitalStatusChagedBy;
		this.printStatusChangedBy = printStatusChangedBy;
		this.clientType = clientType;
	}

	public ClientDetailDTO(String fname, String lname, String compName, Integer businessCategory,
			String businessCategoryOther, String email, String phone1, String address, String state, String city,
			String pincode, String gstNumber, String panNumber, String advertisedIn, BooleanEnum isClientForDigital,
			BooleanEnum isClientForRadio, BooleanEnum isClientForPrint, ClientType clientType) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.compName = compName;
		this.businessCategory = businessCategory;
		this.businessCategoryOther = businessCategoryOther;
		this.email = email;
		this.phone1 = phone1;
		this.address = address;
		this.state = state;
		this.city = city;
		this.pincode = pincode;
		this.gstNumber = gstNumber;
		this.panNumber = panNumber;
		this.advertisedIn = advertisedIn;
		this.isClientForDigital = isClientForDigital;
		this.isClientForRadio = isClientForRadio;
		this.isClientForPrint = isClientForPrint;
		this.clientType = clientType;
	}

	public ClientDetailDTO() {
		super();
	}
	
	
}
