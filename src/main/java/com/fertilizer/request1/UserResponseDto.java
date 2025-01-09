package com.fertilizer.request1;

import com.fertilizer.model.Role;

/**
 * @author Dhiraj
 *
 */
public class UserResponseDto {

	private Long id;

	private Role roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRoles() {
		return roles;
	}

	public void setRoles(Role roles) {
		this.roles = roles;
	}

	public UserResponseDto() {
		super();
	}
}
