package com.fertilizer.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fertilizer.DTO.EditProfileRequestDTO;
import com.fertilizer.DTO.UserHierarchyDTO;
import com.fertilizer.DTO.UserProfileDTO;
import com.fertilizer.model.User;
import com.fertilizer.payload.response.UserProfileDetailDTO;
import com.fertilizer.repository.UserRepository;
import com.fertilizer.request.AdminUserSearch;
import com.fertilizer.request.SignUpRequest;
import com.fertilizer.request.UpdatePaymentPayload;
import com.fertilizer.request1.UserUpdate;
import com.fertilizer.service.ClientService;
import com.fertilizer.service.UserService;
import com.fertilizer.util.CommonUtils;
import com.fertilizer.util.Response;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("")
@CrossOrigin
public class UserController {

	private static final Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	ClientService clientService;

	@Autowired
	CommonUtils commonUtils;

	private ModelMapper modelMapper = new ModelMapper();

	@PostMapping("/emailUniqueness")
	public boolean checkEmail(@RequestBody String email) {
		return userService.checkEmailExists(email);
	}

	@PostMapping("/activateUser")
	public ResponseEntity<Response<String>> activateUser(@RequestBody Long userId) {
		ResponseEntity<Response<String>> result = userService.activateUser(userId);
		return result;
	}

	// working
	@ApiOperation(value = "Add User", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	// @PreAuthorize("hasRole('L3')")
	@PostMapping("/signUp")
	public ResponseEntity<Response<String>> signUpUser(@Valid @RequestBody SignUpRequest payload) {
		logger.debug("signUpUser method called in UserController controller");
		ResponseEntity<Response<String>> result = userService.signUpUser(payload);
		return result;
	}

	@ApiOperation(value = "Update User Payment", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	@PreAuthorize("hasRole('Admin')")
	@PostMapping("/updatePayment")
	public ResponseEntity<Response<String>> updatePayment(@RequestBody UpdatePaymentPayload payload) {
		ResponseEntity<Response<String>> result = userService.updatePayment(payload);
		return result;
	}

	// @PreAuthorize("hasRole('Admin')")
	@GetMapping("/reEntry")
	public ResponseEntity<Response<String>> reRegister() {
		logger.debug("reRegister method called in UserController controller");
		ResponseEntity<Response<String>> result = userService.reRegister();
		return result;
	}

	@ApiOperation(value = "Get All Users", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	// @PreAuthorize("hasRole('L3')")
	@GetMapping("/getAllUsers")
	public ResponseEntity<Response<List<UserHierarchyDTO>>> getUserHierarchy() {
		// User rootUser = userService.getUserById(userId);
		User rootUser = userService.getSignedInUserDTO();
		List<User> userList=new ArrayList<User>();
		userList.add(rootUser);
		List<UserHierarchyDTO> hierarchy = userService.buildUserHierarchyForAdmin(userList);
		return ResponseEntity.ok(Response.ok(hierarchy, "User hierarchy fetched successfully"));
	}

	@ApiOperation(value = "Get All Users by admin", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	// @PreAuthorize("hasRole('Admin')")
	@PostMapping("/getAllUsersByAdmin")
	public ResponseEntity<Response<List<UserHierarchyDTO>>> getAllUsersByAdmin(
			@RequestBody AdminUserSearch userRequestDTO) {
		List<UserHierarchyDTO> allTeam = userService.findAllTeam(userRequestDTO);
		return ResponseEntity.ok(Response.ok(allTeam, "User hierarchy fetched successfully"));

	}

	@ApiOperation(value = "Get Profile", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	// @PreAuthorize("hasRole('L3')")
	@GetMapping("/profile")
	public ResponseEntity<Response<UserProfileDTO>> viewProfile() {
		logger.debug("viewProfile method called in UserController");

		User currentUser = userService.getSignedInUserDTO();

		UserProfileDTO userProfileDTO = new UserProfileDTO(currentUser);

		return ResponseEntity.ok(Response.ok(userProfileDTO, "User profile retrieved successfully"));
	}

	@ApiOperation(value = "Edit Profile", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	// @PreAuthorize("hasRole('L3')")
	@PutMapping("/profile")
	public ResponseEntity<Response<String>> editProfile(@Valid @RequestBody EditProfileRequestDTO payload) {
		logger.debug("editProfile method called in UserController");

		User currentUser = userService.getSignedInUserDTO();

		userService.updateUserProfile(currentUser, payload);

		return ResponseEntity.ok(Response.ok("Profile updated successfully"));
	}

	// SignedIn User
	@ApiOperation(value = "SignedIn User Details", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	@PreAuthorize("hasRole('L3')")
	@GetMapping("/user/me")
	public ResponseEntity<Response<UserProfileDetailDTO>> getUserProfileDetailDTO() {

		logger.debug("getSignedInUser method of UserController is called");
		User user = userService.getSignedInUserDTO();
		UserProfileDetailDTO userProfileDetailDTO = new UserProfileDetailDTO(user);
		return ResponseEntity.ok(Response.ok(userProfileDetailDTO, "User detail fetched succesfully."));

	}

//	// SignedIn User
//	@ApiOperation(value = "SignedIn User Details", response = ResponseEntity.class)
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
//			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
//	@PreAuthorize("hasRole('L3')")
//	@GetMapping("/user/me")
//	public ResponseEntity<Response<UserProfileDetailDTO>> getUserProfileDetailDTO() {
//
//		logger.debug("getSignedInUser method of UserController is called");
//		User user = userService.getSignedInUserDTO();
//		if (null != user.getUsername()) {
//			UserProfileDetailDTO userProfileDetailDTO = userService.getUserProfileDetailDTO(user.getUsername());
//			return ResponseEntity.ok(Response.ok(userProfileDetailDTO, "User detail fetched succesfully."));
//		
//		throw new BadRequestException("User detail not found.");
//	}

	// updateUserMe
	@ApiOperation(value = "Update User Details", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 204, message = "No Data found."), })
	@PreAuthorize("hasRole('User')")
	@PatchMapping("/user/me")
	public ResponseEntity<Response<UserProfileDetailDTO>> updateUserProfileDetailDTO(
			@RequestBody @Valid UserUpdate updateGstInfo, HttpServletRequest request) {

		logger.debug("getSignedInUser method of UserController is called");

		User user = userService.getSignedInUserDTO();
		UserProfileDetailDTO userProfileDetail = userService.updateUserProfileDetail(updateGstInfo, request, false);

		return ResponseEntity.ok(Response.ok(userProfileDetail, "Profile Details Updated Successfully"));
	}

}