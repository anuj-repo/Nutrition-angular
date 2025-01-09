package com.fertilizer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fertilizer.payload.response.JwtAuthenticationResponse;
import com.fertilizer.request1.ChangePasswordDto;
import com.fertilizer.request1.ResetPasswordDto;
import com.fertilizer.request1.UserOtpDTO;
import com.fertilizer.service.PasswordService;
import com.fertilizer.util.Response;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Dhiraj
 *
 */
@RestController
@RequestMapping("")
@CrossOrigin
@Validated
public class PasswordController {
	
	private static final Logger logger = LogManager.getLogger(PasswordController.class);
	
	@Autowired
	private PasswordService passwordService;
	
	@ApiOperation(value = "API to get Recovery email for forgot password otp.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success")})
	@GetMapping("/get-password-recovery-otp")
	public ResponseEntity<Response<Object>> getPasswordRecoveryOtp(@NotBlank(message = "UserName cannot be blank") @RequestParam String username) {
		logger.debug("getPasswordRecoveryMail method of PasswordController is called");
		return passwordService.getPasswordRecoveryOtp(username);
	}
	
	@ApiOperation(value = "verifyOtp forgot password otp ", response = ResponseEntity.class, consumes = "multipart/form-data")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	@PostMapping(value = "/verify-password-recovery-otp")
	public ResponseEntity<Response<String>> verifyPasswordRecoveryOtp(@RequestBody @Valid UserOtpDTO userOtpDTO) {

		logger.debug("verifyOtp method of UserController is called");
		return passwordService.verifyPasswordRecoveryOtp(userOtpDTO);
	}
	
	@ApiOperation(value = "API to reset the password for forget Password user.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success")})
	@PostMapping("/reset-password")
	public ResponseEntity<Response<JwtAuthenticationResponse>> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) {
		logger.debug("resetPassword method of PasswordController is called");
		return passwordService.resetPassword(resetPasswordDto);
	}
	
	@ApiOperation(value = "API to change password.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success")})
	//@PreAuthorize("hasRole('User')")
	@PreAuthorize("hasRole('L3')")
	@PostMapping("/change-password")
	public ResponseEntity<Response<Object>> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto,HttpServletRequest request) {
		logger.debug("changePassword method of PasswordController is called");
		return passwordService.changePassword(changePasswordDto,request);
	}
}
