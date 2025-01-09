package com.fertilizer.config;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fertilizer.enums.Status;
import com.fertilizer.enums.UserType;
import com.fertilizer.payload.response.RoleDTO;
import com.fertilizer.payload.response.UserDTO;


public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 7631849006767102438L;

	private Long id;

	private String fname;

	private String lname;

	private String name;

	private String username;

	private String email;

	private String password;

	private Status status;

	private UserType userType;

	private Set<RoleDTO> roles;

	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(Long id, String fname, String lname, String username, String email, String password,
			Status status, UserType userType, Set<RoleDTO> roles, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.name = fname + " " + lname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.status = status;
		this.userType = userType;
		this.roles = roles;
		this.authorities = authorities;
	}

	public static UserPrincipal create(UserDTO user, Set<RoleDTO> roles) {
		List<GrantedAuthority> authorities = roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleIdentifier())).collect(Collectors.toList());

		return new UserPrincipal(user.getId(), user.getFname(), user.getLname(), user.getUsername(), user.getEmail(),
				user.getPassword(), user.getStatus(), user.getUserType(), roles, authorities);
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param lname the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the userType
	 */
	public UserType getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	/**
	 * @return the roles
	 */
	public Set<RoleDTO> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<RoleDTO> roles) {
		this.roles = roles;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "UserPrincipal [ id=" + id + ", name=" + fname + " +lname" + ", username=" + username + ", email="
				+ email + ", password=" + password + ", status=" + status + ", userType=" + userType + ", roles="
				+ roles + ", authorities=" + authorities + "]";
	}

}
