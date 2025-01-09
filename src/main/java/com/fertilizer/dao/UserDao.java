package com.fertilizer.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fertilizer.model.User;
import com.fertilizer.payload.response.CityDTOResponse;
import com.fertilizer.payload.response.RoleDTO;
import com.fertilizer.payload.response.UserDTO;
import com.fertilizer.payload.response.UserDetailDTO;
import com.fertilizer.payload.response.UserDetailsDTO;
import com.fertilizer.payload.response.UserListResponseDTO;
import com.fertilizer.payload.response.UserProfileDetailDTO;
import com.fertilizer.request.AdminUserSearch;
import com.fertilizer.request1.UserSearchDTO;

public interface UserDao {

	Optional<UserDTO> userData(Long userId);

	Set<RoleDTO> roleData(Long userId);

	Optional<UserDTO> userDataByUserName(String userName);

	Set<RoleDTO> roleDataByUserName(String userName);

	User getUserMe();

	Optional<UserProfileDetailDTO> getUserProfileDetailDTO(String username);

	List<UserListResponseDTO> getUsers(UserSearchDTO userSearchDTO);

	Long count(UserSearchDTO userSearchDTO);

	Optional<UserDetailDTO> getUserDetailDTO(Long id);

	Long countDistinctRegisteredUsers(UserSearchDTO userSearchDTO);

	Optional<UserDetailsDTO> getBasicUserDetailDTO(Long id);

	List<CityDTOResponse> getUserCity();

	Long getUserCityCount();

	List<User> getAllTeam(AdminUserSearch userRequestDTO);

}