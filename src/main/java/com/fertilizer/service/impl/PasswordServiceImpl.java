package com.fertilizer.service.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fertilizer.config.JwtTokenProvider;
import com.fertilizer.exception.BadRequestException;
import com.fertilizer.model.User;
import com.fertilizer.model.UserAuthToken;
import com.fertilizer.payload.response.JwtAuthenticationResponse;
import com.fertilizer.repository.UserAuthTokenRepository;
import com.fertilizer.repository.UserRepository;
import com.fertilizer.request1.ChangePasswordDto;
import com.fertilizer.request1.ResetPasswordDto;
import com.fertilizer.request1.UserOtpDTO;
import com.fertilizer.service.AuthTokenService;
import com.fertilizer.service.PasswordService;
import com.fertilizer.util.Response;

@Service
public class PasswordServiceImpl implements PasswordService {
	private static final Logger logger = LogManager.getLogger(PasswordServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthTokenService authTokenService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserAuthTokenRepository userAuthTokenRepository;

	@Override
	@Transactional
	public ResponseEntity<Response<Object>> getPasswordRecoveryOtp(String username) {

		logger.debug("getPasswordRecoveryMail method of PasswordServiceImpl is called");
		Optional<User> findByUsername = userRepository.findByUsername(username);
		Boolean isPersentAdworks = false;
		if (findByUsername.isPresent()) {
			isPersentAdworks = true;
		}
		if (isPersentAdworks) {
		}
		return null;
	}

	@Transactional
	@Override
	public ResponseEntity<Response<String>> verifyPasswordRecoveryOtp(@Valid UserOtpDTO userOtpDTO) {
		logger.debug("getPasswordRecoveryMail method of PasswordServiceImpl is called");
		Optional<User> findByUsername = userRepository.findByUsername(userOtpDTO.getEmail());
		if (findByUsername.isPresent()) {
			User user = findByUsername.get();
			
		}
		throw new BadRequestException("Invalid Username.");
	}

	@Override
	@Transactional
	public ResponseEntity<Response<JwtAuthenticationResponse>> resetPassword(ResetPasswordDto resetPasswordDto) {
		logger.debug("resetPassword method of PasswordServiceImpl is called");
		Optional<User> findByForgetPasswordToken = userRepository
				.findByForgetPasswordToken(resetPasswordDto.getToken());
		if (findByForgetPasswordToken.isPresent()) {
			User user = findByForgetPasswordToken.get();
			
			
			
		}
		throw new BadRequestException("Invalid token.");
	}

	@Override
	@Transactional
	public ResponseEntity<Response<Object>> changePassword(ChangePasswordDto changePasswordDto,
			HttpServletRequest request) {
		logger.debug("changePassword method of PasswordServiceImpl is called");
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<User> findByUsername = userRepository.findByUsername(username);
		if (findByUsername.isPresent()) {
			User user = findByUsername.get();
			Long tokenId = getTokenId(request);
			Optional<UserAuthToken> findByTokenId = userAuthTokenRepository.findById(tokenId);
			if (findByTokenId.isPresent()) {
				UserAuthToken userAuthToken = findByTokenId.get();
				
				
			}
			throw new BadRequestException("Invalid token id.");
		}
		throw new BadRequestException("Invalid Username.");
	}

	public Long getTokenId(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			String authToken = bearerToken.substring(7, bearerToken.length());
			return tokenProvider.getIdFromJWT(authToken);
		}
		throw new BadRequestException("Invalid token.");
	}

}