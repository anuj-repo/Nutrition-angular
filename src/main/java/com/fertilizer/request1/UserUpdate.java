package com.fertilizer.request1;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.fertilizer.annotation.BlankOrPattern;
import com.fertilizer.enums.CompanyTurnOver;
import com.fertilizer.enums.UserType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdate {
	@NotNull
	private String userId;
	@NotBlank(message = "{validation.fname.NotBlank}")
	@Size(max = 50, message = "{validation.fname.Size}")
	private String fname;
	@NotBlank(message = "{validation.lname.NotBlank}")
	@Size(max = 50, message = "{validation.lname.Size}")
	private String lname;
	@Pattern(regexp = "\\d{10,20}", message = "{validation.phone.Pattern}")
	@NotBlank(message = "{validation.phone.NotBlank}")
	private String phone1;
	@NotBlank(message = "{validation.companyName.NotBlank}")
	@Size(max = 80, message = "{validation.companyName.Size}")
	private String company;
	private Integer businessCategory;
	@Size(max = 80, message = "{validation.businessCategoryOther.Size}")
	private String businessCategoryOther;
	private CompanyTurnOver companyTurnOver;
	@BlankOrPattern(regexp ="[a-zA-Z0-9]{15}$",message="{validation.gst.Size}")
	private String gstNumber;
	@BlankOrPattern(regexp = "^([A-Z]){5}([0-9]){4}([A-Z]){1}$", message = "{validation.pan.Pattern}")
	private String panNumber;
	@Length(max=255,message= "{validation.address.Size}")	
	private String address;
	@Pattern(regexp = "^[ a-zA-Z]*$", message = "{validation.state.Pattern}")
	private String state;
	@Pattern(regexp = "^[ a-zA-Z]*$", message = "{validation.city.Pattern}")
	private String city;
	@BlankOrPattern(regexp = "^[0-9]{6}$", message = "{validation.pin.Pattern}")
	private String pincode;
	private UserType userType;
	private List<UserAddressDTORequest> userAddressDTO;
	private String roleIdentifier;

	private String clients;
	public UserUpdate(String userId,String fname,String lname,String phone1,String company,
			Integer businessCategory,String businessCategoryOther,CompanyTurnOver companyTurnOver, 
			String gstNumber, String panNumber, String address, String state, String city,String pincode,UserType userType,
			List<UserAddressDTORequest> userAddressDTO,String roleIdentifier,String clients) {
		super();
		this.userId = userId;
		this.fname = fname;
		this.lname = lname;
		this.phone1 = phone1;
		this.company = company;
		this.businessCategory = businessCategory;
		this.businessCategoryOther = businessCategoryOther;
		this.companyTurnOver = companyTurnOver;
		this.gstNumber = gstNumber;
		this.panNumber = panNumber;
		this.address = address;
		this.state = state;
		this.city = city;
		this.pincode=pincode;
		this.userType=userType;
		this.userAddressDTO = userAddressDTO;
		this.roleIdentifier = roleIdentifier;
		this.clients = clients;
	}

	public UserUpdate() {
		super();
	}
	
	
}
