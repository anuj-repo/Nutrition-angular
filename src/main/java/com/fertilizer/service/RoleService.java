package com.fertilizer.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fertilizer.enums.Status;
import com.fertilizer.model.Role;
import com.fertilizer.model.User;
import com.fertilizer.payload.response.UserDetailDTO;
import com.fertilizer.request1.RoleBasedBillingUpdateUserDTO;
import com.fertilizer.request1.RoleBasedUpdateUserDTO;
import com.fertilizer.request1.RoleBasedUserCreateDTO;
import com.fertilizer.util.Response;

public interface RoleService {
	Response<String> registerUserInPortal(RoleBasedUserCreateDTO roleBasedUserCreateDTO, User user);

	
	

	
	Response<UserDetailDTO> getSubUserRegistrationDetailById(Long userId);

	Response<String> updateSubUserProfile(RoleBasedUpdateUserDTO roleBasedUserCreateDTO, Long userId,
			HttpServletRequest request);

	Response<String> updateSubUserDetailsBySuperAdmin(RoleBasedUpdateUserDTO roleBasedUpdateUserDTO, Long userId);

	void sendReminderEmailForSubUser();

	
	String markUserStatus(Long userId, Status st);

	Integer getSubUserDetailsForOrderLimit(Long userId);

	Response<String> resendSubUserOnboardingLink(User loggedInUser, Long subUserId);

	Integer getSubUserDetailsForOrderEditLimit(Long userId);

	String updateSubUserBillingProfile(RoleBasedBillingUpdateUserDTO roleBasedUserCreateDTO, User user);
}
