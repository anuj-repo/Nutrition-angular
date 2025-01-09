package com.fertilizer.payload.response;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fertilizer.enums.ApprovalStatus;
import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.CompanySize;
import com.fertilizer.enums.CompanyTurnOver;
import com.fertilizer.enums.Salutation;
import com.fertilizer.enums.UserType;
import com.fertilizer.model.Permission;
import com.fertilizer.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileDetailDTO {
//	@JsonIgnore
	private Long id;

	private String email;

	private String contact;

	private String address;

	private String userImageUrl;
	
	private Long roleId;
	private String role;

	private Integer parentRoleId;
	
	private Long parentId;
	
	private Salutation salutation;

	private String fname;

	private String lname;

	private String designation;

	private String panNumber;

	private String city;

	private String state;

	private String pincode;

	private String userImage;

	private BooleanEnum isDetailCompleted;

	private UserType userType;

	private BooleanEnum isPhoneVerified;

	private List<UserAddressDTO> userAddressDTO;

	private Map<String, Permission> permissions;


	public UserProfileDetailDTO(Long id, String name, String email, String contact,  String address,
			String userImageUrl,  String role) {
		super();
		this.id = id;
		this.fname = name;
		this.email = email;
		this.contact = contact;
		
		this.address = address;
		this.userImageUrl = userImageUrl;
		
		this.role = role;
		
	}

	public UserProfileDetailDTO(Long id, String email, String contact, String address, String userImageUrl, String role, Salutation salutation,
			String fname, String lname, String designation,
			String compName, BooleanEnum hasAdvertisedBefore,
			 BooleanEnum receiveNotification,
			String panNumber, String city, String state, String pincode, String userImage,
			BooleanEnum isDetailCompleted, UserType userType, BooleanEnum isPhoneVerified
			) {
		super();
		this.id = id;
		this.email = email;
		this.contact = contact;
		this.address = address;
		this.userImageUrl = userImageUrl;
		this.role = role;
		this.salutation = salutation;
		this.fname = fname;
		this.lname = lname;
		this.designation = designation;
		this.panNumber = panNumber;
		this.state = state;
		this.city = city;
		this.pincode = pincode;
		this.userImage = userImage;
		this.isDetailCompleted = isDetailCompleted;
		this.userType = userType;
		this.isPhoneVerified = isPhoneVerified;
		
	}
	
	public UserProfileDetailDTO(Long id, Long parentId,String email, String contact,  String address,
			 String userImageUrl, Long roleId, String role,
			 Integer parentRoleId,  Salutation salutation,
			String fname, String lname, CompanySize companySize, String designation,
			String compName,  BooleanEnum receiveNotification, 
			String panNumber, String city, String state, String pincode, String userImage,
			BooleanEnum isDetailCompleted, UserType userType, BooleanEnum isPhoneVerified) {

		super();
		this.id = id;
		this.parentId = parentId;
		this.email = email;
		this.contact = contact;
		this.address = address;
		this.userImageUrl = userImageUrl;
		this.roleId = roleId;
		this.role = role;
		this.parentRoleId = parentRoleId;
		this.salutation = salutation;
		this.fname = fname;
		this.lname = lname;
		this.designation = designation;
		this.panNumber = panNumber;
		this.state = state;
		this.city = city;
		this.pincode = pincode;
		this.userImage = userImage;
		this.isDetailCompleted = isDetailCompleted;
		this.userType = userType;
		this.isPhoneVerified = isPhoneVerified;
		
	}
	
	public UserProfileDetailDTO(User user) {
        this.id = user.getId(); // Assuming BaseModel has getId()
        this.email = user.getEmail();
        this.contact = user.getMobileNumber(); // Use mobileNumber for contact
        this.address = user.getAddress();
        this.userImageUrl = user.getUserImage(); // Assuming userImage contains the URL
        this.roleId = user.getRole() != null ? user.getRole().getId() : null; // Assuming Role has getId()
        this.role = user.getRole() != null ? user.getRole().getRoleName() : null; // Assuming Role has getName()
        this.salutation = user.getSalutation();
        this.fname = user.getFname();
        this.lname = user.getLname();
//        this.designation = user.getDesignation(); // Add this field if it exists in User
//        this.panNumber = user.getPanNumber(); // Assuming this field exists in User
        this.city = user.getCity();
        this.state = user.getState();
        this.pincode = user.getPincode();
        this.userImage = user.getUserImage(); // If different from userImageUrl
        this.isDetailCompleted = user.getIsDetailCompleted();
        this.userType = user.getUserType();
        this.isPhoneVerified = user.getIsPhoneVerified();
    }
}
