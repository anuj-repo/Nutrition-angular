package com.fertilizer.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fertilizer.enums.Status;
import com.fertilizer.enums.UserType;

/**
 * @author Dhiraj
 *
 */
public class UserDTO {

	private Long id;
	private String username;
	private UserType userType;
	private String fname;
	private String lname;
	private String email;
	private Status status;
	@JsonIgnore
	private String password;
	
	public UserDTO(Status status) {
		super();
		this.status = status;
	}
	
	public UserDTO(Long id,String username, UserType userType, String fname, String lname, String email, Status status) {
		super();
		this.id = id;
		this.username = username;
		this.userType = userType;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.status = status;
	}
	
	public UserDTO(Long id,String username, UserType userType, String fname, String lname, String email, Status status,String password) {
		super();
		this.id = id;
		this.username = username;
		this.userType = userType;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.status = status;
		this.password = password;
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
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
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
	
}
