package com.fertilizer.request1;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fertilizer.enums.Status;

public class UserUpdateDto {

	@NotNull
	private Long id;

	
	private String username;

	@Size(min = 2, max = 40, message = "First name should be between 2 to 40 characters.")
	@Pattern(regexp = "[A-Za-z_0-9.]*")
	private String fname;

	@Size(min = 2, max = 40, message = "Last name should be between 2 to 40 characters.")
	@Pattern(regexp = "[A-Za-z_0-9.]*")
	private String lname;

	@Size(min = 1, max = 40, message = "Employee Code should be between 1 to 40 characters.")
	@Pattern(regexp = "[A-Z_a-z.0-9/]*")
	private String empCode;

	@Email(message = "{validation.email.pattern}")
	private String email;

	private Status status;

	private Long roleId;

	private String roleIdentifier;

	private Long companyId;
	private Integer countryId;
	private String regionsId;
	private String zonesId;
	private String groupsId;
	private Long storeId;

	private String image;
	private String address;

	@Size(min = 10, max = 20, message = "Invalid Phone Number")
	@Pattern(regexp = "\\d{10,20}", message = "Invalid Phone Number")
	private String phone1;
	private String phone2;

	@Pattern(regexp = "\\d+")
	private String phone1StdCode;

	private String phone2StdCode;

	/**
	 * @return the roleIdentifier
	 */
	public String getRoleIdentifier() {
		return roleIdentifier;
	}

	/**
	 * @param roleIdentifier the roleIdentifier to set
	 */
	public void setRoleIdentifier(String roleIdentifier) {
		this.roleIdentifier = roleIdentifier;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the companyId
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the countryId
	 */
	public Integer getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the regionsId
	 */
	public String getRegionsId() {
		return regionsId;
	}

	/**
	 * @param regionsId the regionsId to set
	 */
	public void setRegionsId(String regionsId) {
		this.regionsId = regionsId;
	}

	/**
	 * @return the zonesId
	 */
	public String getZonesId() {
		return zonesId;
	}

	/**
	 * @param zonesId the zonesId to set
	 */
	public void setZonesId(String zonesId) {
		this.zonesId = zonesId;
	}

	/**
	 * @return the groupsId
	 */
	public String getGroupsId() {
		return groupsId;
	}

	/**
	 * @param groupsId the groupsId to set
	 */
	public void setGroupsId(String groupsId) {
		this.groupsId = groupsId;
	}

	/**
	 * @return the storeId
	 */
	public Long getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}

	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the roles
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the phone1
	 */
	public String getPhone1() {
		return phone1;
	}

	/**
	 * @param phone1 the phone1 to set
	 */
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	/**
	 * @return the phone2
	 */
	public String getPhone2() {
		return phone2;
	}

	/**
	 * @param phone2 the phone2 to set
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone1StdCode() {
		return phone1StdCode;
	}

	public void setPhone1StdCode(String phone1StdCode) {
		this.phone1StdCode = phone1StdCode;
	}

	public String getPhone2StdCode() {
		return phone2StdCode;
	}

	public void setPhone2StdCode(String phone2StdCode) {
		this.phone2StdCode = phone2StdCode;
	}

}
