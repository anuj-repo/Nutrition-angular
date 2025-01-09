package com.fertilizer.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Table(name = "cookie_consent")
@Entity
@Getter
@Setter
@DynamicUpdate
public class CookieConsent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
//	@JsonBackReference
//	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, optional = false)
//	@JoinColumn(name = "user_id")
//	private User user;
	Long userId;
	
	@Column(updatable = false)
	// @JsonIgnore
	protected Date timestamp;
	
	private String cookieName;
	
	private String sessionId;
	
	private Boolean consentGiven;
	
	private String consentMethod;
	
	private String ipAddress;
	
}