package com.fertilizer.payload.response;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.CompanySize;
import com.fertilizer.enums.CompanyTurnOver;
import com.fertilizer.enums.Salutation;
import com.fertilizer.enums.Status;
import com.fertilizer.enums.UserType;

import com.fertilizer.model.Role;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author kabir
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDetailDTO {
	@JsonIgnore
	private Long id;

	private String email;

	private String phone1;

	private String companyName;

	private String address;

	private Long loyaltyPoint;

	private Double voucherAmount;

	private String userImageUrl;

	private String RoleIdentifier;

	private Salutation salutation;

	private String fname;

	private String lname;

	private Role role;

	private CompanySize companySize;

	private String designation;

	private String compName;

	private Integer businessCategory;

	private String businessCategoryOther;

	private BooleanEnum hasAdvertisedBefore;

	private String advertisedIn;

	private String advertisedOtherIn;

	private BooleanEnum receiveNotification;

	private CompanyTurnOver companyTurnOver;

	private String gstNumber;

	private String panNumber;

	private String city;

	private String state;

	private String pincode;

	private BooleanEnum isDetailCompleted;

	private UserType userType;

	private BooleanEnum isPhoneVerified;

	private List<UserAddressDTO> userAddressDTO;

	private Double previousPurchase;

	private Integer voucherCatId;


	private Double totalVoucherAmount;

	private BooleanEnum isAccreditedCustomer;
	
	private BooleanEnum isPremiumPrintCustomer;
	
	private BooleanEnum isPremiumRadioCustomer;
	
	private BooleanEnum isPremiumDigitalCustomer;

	private BooleanEnum isPdcEnabled;

	private Long ownerId;

	private String clients;

	private String roleIcon;

	//private Set<Clients> client;

	private String roleName;
	
	private Integer roleWeight;

	private Status status;

	private List<String> selfClientName;

	private List<String> ownerClientName;
	
	private Long userId;

	private Integer completedStep;

	private BooleanEnum isPasswordUpdated;

	public UserDetailDTO(Long id, String email, String phone1, String companyName, String address,
			Salutation salutation, String fname, String lname, Role role, CompanySize companySize, CompanyTurnOver companyTurnOver,
			String designation, String compName, Integer businessCategory, String businessCategoryOther,
			BooleanEnum hasAdvertisedBefore, String advertisedIn, String advertisedOtherIn,
			BooleanEnum receiveNotification, String gstNumber, String panNumber, String city, String state,
			String pincode, BooleanEnum isDetailCompleted, UserType userType, BooleanEnum isPhoneVerified,
			Long loyaltyPoint, Double voucherAmount, Integer voucherCatId, BooleanEnum isAccreditedCustomer,BooleanEnum isPremiumPrintCustomer,BooleanEnum isPremiumRadioCustomer,BooleanEnum isPremiumDigitalCustomer, BooleanEnum isPdcEnabled, Long ownerId) {
		this.id = id;
		this.email = email;
		this.phone1 = phone1;
		this.companyName = companyName;
		this.address = address;
		this.loyaltyPoint = loyaltyPoint;
		this.voucherAmount = voucherAmount;
		this.salutation = salutation;
		this.fname = fname;
		this.lname = lname;
		this.role = role;
		this.companySize = companySize;
		this.designation = designation;
		this.compName = compName;
		this.businessCategory = businessCategory;
		this.businessCategoryOther = businessCategoryOther;
		this.hasAdvertisedBefore = hasAdvertisedBefore;
		this.advertisedIn = advertisedIn;
		this.advertisedOtherIn = advertisedOtherIn;
		this.receiveNotification = receiveNotification;
		this.companyTurnOver = companyTurnOver;
		this.gstNumber = gstNumber;
		this.panNumber = panNumber;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.isDetailCompleted = isDetailCompleted;
		this.userType = userType;
		this.isPhoneVerified = isPhoneVerified;
		this.voucherCatId = voucherCatId;
		this.isAccreditedCustomer = isAccreditedCustomer;
		this.isPremiumPrintCustomer=isPremiumPrintCustomer;
		this.isPremiumRadioCustomer=isPremiumRadioCustomer;
		this.isPremiumDigitalCustomer=isPremiumDigitalCustomer;
		this.isPdcEnabled = isPdcEnabled;
		this.ownerId = ownerId;
	}

	public UserDetailDTO(Long userId, String roleName, String email, Role role,String designation,
			Status status, String roleIcon, String fname, String lname) {
		this.userId = id;
		this.roleName = roleName;
		this.email = email;
		this.role = role;
		this.designation = designation;
		this.status = status;
		this.roleIcon = roleIcon;
		this.fname = fname;
		this.lname = lname;

	}
}
