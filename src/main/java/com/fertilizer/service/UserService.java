package com.fertilizer.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.fertilizer.DTO.EditProfileRequestDTO;
import com.fertilizer.DTO.UserHierarchyDTO;
import com.fertilizer.model.User;
import com.fertilizer.payload.response.UserProfileDetailDTO;
import com.fertilizer.request.AdminUserSearch;
import com.fertilizer.request.SignUpRequest;
import com.fertilizer.request.UpdatePaymentPayload;
import com.fertilizer.request1.UserCreateDto;
import com.fertilizer.request1.UserUpdate;
import com.fertilizer.util.Response;

public interface UserService {

	User getSignedInUserDTO();

	void checkUserNameUniqueness(UserCreateDto userCreateDto);

	ResponseEntity<Response<String>> signUpUser(SignUpRequest payload);

	ResponseEntity<Response<String>> updateUserProfile(User currentUser, @Valid EditProfileRequestDTO payload);

	// User registerNewUser(User user);

	UserProfileDetailDTO getUserProfileDetailDTO(String username);

	UserProfileDetailDTO updateUserProfileDetail(UserUpdate userUpdate, HttpServletRequest request,
			Boolean isUpdateByAdmin);

	List<UserHierarchyDTO> findAllTeam(AdminUserSearch userRequestDTO);

	UserHierarchyDTO buildUserHierarchy(User rootUser);

	boolean checkEmailExists(String email);

	ResponseEntity<Response<String>> reRegister();

	ResponseEntity<Response<String>> activateUser(Long userId);

	ResponseEntity<Response<String>> updatePayment(UpdatePaymentPayload payload);

	List<UserHierarchyDTO> buildUserHierarchyForAdmin(List<User> userList);

}
