package com.fertilizer.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.fertilizer.payload.response.JwtAuthenticationResponse;
import com.fertilizer.request1.ChangePasswordDto;
import com.fertilizer.request1.ResetPasswordDto;
import com.fertilizer.request1.UserOtpDTO;
import com.fertilizer.util.Response;

/**
 * @author Dhiraj
 *
 */
public interface PasswordService {

	ResponseEntity<Response<Object>> getPasswordRecoveryOtp(String username);

	ResponseEntity<Response<JwtAuthenticationResponse>> resetPassword(ResetPasswordDto resetPasswordDto);

	ResponseEntity<Response<Object>> changePassword(ChangePasswordDto resetPasswordDto, HttpServletRequest request);

	ResponseEntity<Response<String>> verifyPasswordRecoveryOtp(@Valid UserOtpDTO userOtpDTO);
}
