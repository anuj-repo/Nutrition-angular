package com.fertilizer.util;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dhiraj
 *
 */
@Data
@NoArgsConstructor
public class SSOResponseData {
	
	private String name;
	
	private String email;
	
	private Long clientId;
	
	private String mobileNumber;
	
	private Boolean registered;
	
	private Boolean signUp;

	private Boolean valid;

	
	
}
