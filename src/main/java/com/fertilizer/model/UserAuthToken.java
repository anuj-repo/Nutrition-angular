package com.fertilizer.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.Source;
import com.fertilizer.enums.Status;

import lombok.Getter;
import lombok.Setter;


/**
 * @author Dhiraj
 *
 */
@Entity
@Table(name = "user_auth_tokens")
@Getter
@Setter
public class UserAuthToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9194011994326549412L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Source loggedInFromSource;
	
	private String authToken;

	private String refreshToken;
	
	private String ssoAuthToken;

	private Long userId;

	private Status status;
	
	private BooleanEnum isExpired;
	
	private Date createdAt;
	
	private Date expiresAt;
	
	private Date loggedOutAt;
}
