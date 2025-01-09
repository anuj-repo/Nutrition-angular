package com.fertilizer.payload.response;

import java.io.Serializable;

import com.fertilizer.enums.UserType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupResponseDTO implements Serializable{
	
	
	private static final long serialVersionUID = 2003438089002729537L;

	private String email;
	
	private Integer completedStep;
	
	private UserType userType;
	
	public SignupResponseDTO(String email, Integer completedStep) {
		this.email=email;
		this.completedStep=completedStep;
	}
	

	public SignupResponseDTO(String email, Integer completedStep,UserType userType2) {
		this.email=email;
		this.completedStep=completedStep;
		this.userType=userType2;
	}
}
