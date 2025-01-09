package com.fertilizer.payload.response;

import java.io.Serializable;

import com.fertilizer.enums.EntityConstant;

public class RoleDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String roleName;

	private String roleIdentifier;

	public RoleDTO(String roleName, String roleIdentifier, Long id) {
		super();
		this.roleName = roleName;
		this.roleIdentifier = roleIdentifier;
		this.id = id;
	}

	public RoleDTO(Long id, String roleName, String roleIdentifier) {
		super();
		this.roleName = roleName;
		this.roleIdentifier = roleIdentifier;
		this.id = id;
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
		if (roleIdentifier.contains(EntityConstant.ROLE_PREAPPANDER_FOR_SECURITY.getName()))
			this.roleIdentifier = roleIdentifier;
		this.roleIdentifier = EntityConstant.ROLE_PREAPPANDER_FOR_SECURITY.getName() + roleIdentifier;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
