package com.fertilizer.payload.response;

public class UserOnboardingDTO {
	
	private Long id;
	private String fname;
	private String lname;
	private String email;
	private String stdCode;
	private String phone;
	private String username;
	
	
	public UserOnboardingDTO(Long id, String fname, String lname, String email, String stdCode, String phone) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.stdCode = stdCode;
		this.phone = phone;
	}
	
	
	public UserOnboardingDTO(Long id, String fname, String lname, String email, String stdCode, String phone,
			String username) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.stdCode = stdCode;
		this.phone = phone;
		this.username = username;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStdCode() {
		return stdCode;
	}
	public void setStdCode(String stdCode) {
		this.stdCode = stdCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
