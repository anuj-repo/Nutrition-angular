package com.fertilizer.request1;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fertilizer.enums.BooleanEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CookieConsentDto {
	
	@Size(max = 50, message = "{validation.fname.Size}")
	private String fname;

	@NotNull(message="Consent method is required")
	private String consentMethod;
	
	@NotNull(message=" Whether cookie consent given is required")
	private BooleanEnum consentGiven;
	
	private String ipAddress;
	
	private String sessionId;
	
}

