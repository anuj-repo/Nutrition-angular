package com.fertilizer.payload.response;

import com.fertilizer.enums.Status;
import com.fertilizer.enums.UserLevel;

public class LoginDataProcessDTO {
	
	private Long userId;
	private String username;
	private String userType;  
	private Status status;
	private String password;
	
	private Integer countryId;
	private String regionsId;
	private String zonesId;
	private String groupsId;
	private UserLevel userLevel;
	
	private Long companyId;
	private String compName;
	private String compCode;
	
	private Long storeId;
	private String storeName;
	private RoleDTO role;
	
	private Status companyStatus;
	private Status storeStatus;
	
	
	public LoginDataProcessDTO(Long userId, String username, String userType, Status status, String password,
			Integer countryId, String regionsId, String zonesId, String groupsId, UserLevel userLevel) {
		super();
		this.userId = userId;
		this.username = username;
		this.userType = userType;
		this.status = status;
		this.password = password;
		this.countryId = countryId;
		this.regionsId = regionsId;
		this.zonesId = zonesId;
		this.groupsId = groupsId;
		this.userLevel = userLevel;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public String getRegionsId() {
		return regionsId;
	}
	public void setRegionsId(String regionsId) {
		this.regionsId = regionsId;
	}
	public String getZonesId() {
		return zonesId;
	}
	public void setZonesId(String zonesId) {
		this.zonesId = zonesId;
	}
	public String getGroupsId() {
		return groupsId;
	}
	public void setGroupsId(String groupsId) {
		this.groupsId = groupsId;
	}
	public UserLevel getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(UserLevel userLevel) {
		this.userLevel = userLevel;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getCompCode() {
		return compCode;
	}
	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}
	public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public RoleDTO getRole() {
		return role;
	}
	public void setRole(RoleDTO role) {
		this.role = role;
	}

	public Status getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(Status companyStatus) {
		this.companyStatus = companyStatus;
	}

	public Status getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(Status storeStatus) {
		this.storeStatus = storeStatus;
	}
	
	
	
	

}
