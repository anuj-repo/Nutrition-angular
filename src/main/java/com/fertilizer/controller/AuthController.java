package com.fertilizer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fertilizer.config.JwtTokenProvider;
import com.fertilizer.exception.BadRequestException;
import com.fertilizer.payload.response.JwtAuthenticationResponse;
import com.fertilizer.request1.LoginRequest;
import com.fertilizer.service.AuthTokenService;
import com.fertilizer.service.impl.EmailService;
import com.fertilizer.util.CommonUtils;
import com.fertilizer.util.Response;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	private static final Logger LOGGER = LogManager.getLogger(AuthController.class);

	@Autowired
	private AuthTokenService authTokenService;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	CommonUtils commonUtils;
	
	@Autowired
	EmailService emailService;

	@PostMapping("/signin")
	public ResponseEntity<Response<JwtAuthenticationResponse>> authenticateUser(
			@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("authenticateUser method called in AuthController controller");
		ResponseEntity<Response<JwtAuthenticationResponse>> result = authTokenService.authenticateUser(loginRequest,
				request);
		//emailService.sendEmail("akhileshkumar6641@gmail.com", "Test Mukul Chauhan", "Hi Test User");
		return result;
	}

	@PostMapping("/signout")
	public ResponseEntity<Response<Object>> logoutAuthenticateUser(HttpServletRequest request,
			HttpServletResponse response) {

		LOGGER.debug("logoutAuthenticateUser method called for signout endpoint");
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			String authToken = bearerToken.substring(7, bearerToken.length());
			Long tokenId = tokenProvider.getIdFromJWT(authToken);
			if (tokenId != null) {
				return authTokenService.expireUserAuthToken(tokenId);
			}
			throw new BadRequestException("unable to logout.");
		}
		throw new BadRequestException("unable to logout.");
	}

}