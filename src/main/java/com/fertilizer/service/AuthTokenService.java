package com.fertilizer.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.fertilizer.model.User;
import com.fertilizer.payload.response.JwtAuthenticationResponse;
import com.fertilizer.request1.LoginRequest;
import com.fertilizer.util.Response;

public interface AuthTokenService {

	ResponseEntity<Response<JwtAuthenticationResponse>> authenticateUser(LoginRequest loginRequest,HttpServletRequest request);
	
	ResponseEntity<Response<JwtAuthenticationResponse>> loginWithoutPassword(User user, String message,String ssoToken);

	ResponseEntity<Response<Object>> expireUserAuthToken(long tokenId);
}
