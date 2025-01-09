package com.fertilizer.payload.response;

import com.fertilizer.enums.EntityConstant;
import com.fertilizer.enums.UserLevel;

public class UserEditResponseDTO {

	private Long id;
	private String username;
	private String userType;
	private String fname;
	private String lname;
	private String email;
	private Long companyId;
	private String companyName;
	private UserLevel userLevel;
	private Long roleId;
	private String roleName;
	private String roleIdentifier;

	private String phone1;
	private String phone1StdCode;

	public UserEditResponseDTO() {
	}

	public UserEditResponseDTO(Long id, String username, String userType, String fname, String lname, String email,
			Long companyId, String companyName, UserLevel userLevel, Long roleId, String roleName,
			String roleIdentifier, String phone1, String phone1StdCode) {
		super();
		this.id = id;
		this.username = username;
		this.userType = userType;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.companyId = companyId;
		this.companyName = companyName;
		this.userLevel = userLevel;
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleIdentifier = roleIdentifier;
		this.phone1 = phone1;
		this.phone1StdCode = phone1StdCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public UserLevel getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(UserLevel userLevel) {
		this.userLevel = userLevel;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {

		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleIdentifier() {
		if (roleIdentifier.contains(EntityConstant.ROLE_PREAPPANDER_FOR_SECURITY.getName()))
			return roleIdentifier;
		return EntityConstant.ROLE_PREAPPANDER_FOR_SECURITY.getName() + roleIdentifier;
	}

	public void setRoleIdentifier(String roleIdentifier) {
		this.roleIdentifier = roleIdentifier;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone1StdCode() {
		return phone1StdCode;
	}

	public void setPhone1StdCode(String phone1StdCode) {
		this.phone1StdCode = phone1StdCode;
	}

}
