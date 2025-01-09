package com.fertilizer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fertilizer.enums.Status;
import com.fertilizer.model.Role;
import com.fertilizer.model.User;
import com.fertilizer.payload.response.UserDetailDTO;
import com.fertilizer.payload.response.UserOderDetailsDTO;
import com.fertilizer.request1.RoleBasedUpdateUserDTO;
import com.fertilizer.request1.RoleBasedUserCreateDTO;
import com.fertilizer.service.RoleService;
import com.fertilizer.service.UserService;
import com.fertilizer.util.Response;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	private static final Logger logger = LogManager.getLogger(RoleController.class);

	public RoleController() {
	}

	
	//not using
	@ApiOperation(value = "Add Role Based User for step 1", response = ResponseEntity.class, consumes = "multipart/form-data")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	@PostMapping(value = "/addUser")
	// @PreAuthorize("hasRole('User')")
	@PreAuthorize("hasRole('L2')")
	public ResponseEntity<Response<String>> addUser(@RequestBody @Valid RoleBasedUserCreateDTO roleBasedUserCreateDTO) {
		logger.debug("addUser method of Role Controller is called");
		User user = userService.getSignedInUserDTO();
		return ResponseEntity.ok(roleService.registerUserInPortal(roleBasedUserCreateDTO, user));
	}
	
//	@PostMapping({"/createNewRole"})
//    public Role createNewRole(@RequestBody Role role) {
//        return roleService.createNewRole(role);
//    }

	@ApiOperation(value = "Resend Sub User Onboarding Link", response = ResponseEntity.class, consumes = "multipart/form-data")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	@PostMapping(value = "/resend-user-onboarding-link")
	// @PreAuthorize("hasRole('User')")
	@PreAuthorize("hasRole('L2')")
	public ResponseEntity<Response<String>> resendSubUserOnboardingLink(@RequestParam Long subUserId) {
		logger.debug("resendSubUserOnboardingLink method of Role Controller is called");
		User user = userService.getSignedInUserDTO();
		return ResponseEntity.ok(roleService.resendSubUserOnboardingLink(user, subUserId));
	}



	@ApiOperation(value = "Get User Details of Sub User stored by super user", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	@GetMapping("/sub-user-registered-detail")
	public ResponseEntity<Response<UserDetailDTO>> getSubUserRegistrationData(@RequestParam Long userId) {

		logger.debug("getSubUserRegistrationData method of UserController is called");
		return ResponseEntity.ok(roleService.getSubUserRegistrationDetailById(userId));
	}

	@ApiOperation(value = "Update Sub User Profile", response = ResponseEntity.class, consumes = "multipart/form-data")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	@PutMapping(value = "/update-sub-user-profile/{userId}")
	public ResponseEntity<Response<String>> updateSubUserProfile(@PathVariable Long userId,
			@RequestBody @Valid RoleBasedUpdateUserDTO roleBasedUserCreateDTO, HttpServletRequest request) {
		logger.debug("addSubUserDetails method of Role Controller is called.");
		return ResponseEntity.ok(roleService.updateSubUserProfile(roleBasedUserCreateDTO, userId, request));
	}

	@ApiOperation(value = "Update Sub User Details By Super Admin", response = ResponseEntity.class, consumes = "multipart/form-data")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	@PutMapping(value = "/update-sub-user-details/{userId}")
	public ResponseEntity<Response<String>> updateSubUserDetailsBySuperAdmin(@PathVariable Long userId,
			@RequestBody @Valid RoleBasedUpdateUserDTO roleBasedUpdateUserDTO) {
		logger.debug("addSubUserDetails method of Role Controller is called.");
		return ResponseEntity.ok(roleService.updateSubUserDetailsBySuperAdmin(roleBasedUpdateUserDTO, userId));
	}

	@ApiOperation(value = "Activate or Deactivate Sub User", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	@PreAuthorize("hasRole('L2')")
	@PatchMapping
	public ResponseEntity<Response<Object>> activateOrDeactivatesubUser(@RequestParam(required = true) Long userId,
			@RequestParam(required = true) String status) {
		logger.debug("activateOrDeactivateSubUser method of RoleController is called");
		Status st = Status.forStatusName(status);
		String message = roleService.markUserStatus(userId, st);
		return ResponseEntity.ok(Response.ok(message));
	}

	@ApiOperation(value = "Fetched Order Count Of SignedIn User", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	@PreAuthorize("hasRole('User')")
	@GetMapping("/get-order-count")
	public ResponseEntity<Response<UserOderDetailsDTO>> getOrderCountForUser(@RequestParam Long userId) {
		logger.debug("getOrderCountForUser method of Role Controller is called");
		UserOderDetailsDTO userOderDetailsDTO = new UserOderDetailsDTO();
		Integer orderCount = roleService.getSubUserDetailsForOrderLimit(userId);
		userOderDetailsDTO.setOrderCount(orderCount);
		return ResponseEntity.ok(Response.ok(userOderDetailsDTO, "Order Count Fetched Successfully"));
	}
}
