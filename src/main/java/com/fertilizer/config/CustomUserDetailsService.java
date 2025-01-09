package com.fertilizer.config;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fertilizer.dao.UserDao;
import com.fertilizer.exception.ResourceNotFoundException;
import com.fertilizer.payload.response.RoleDTO;
import com.fertilizer.payload.response.UserDTO;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	@Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username){
		Optional<UserDTO> optionalUserDTO = userDao.userDataByUserName(username);
		if(optionalUserDTO.isPresent())
		{
			Set<RoleDTO> roleDTO = userDao.roleDataByUserName(username);
	        return UserPrincipal.create(optionalUserDTO.get(),roleDTO);
		}
		throw new ResourceNotFoundException("User not found with username or email : " + username);
    }
    
    
    @Transactional(readOnly=true)
    public UserDetails loadUserById(Long id) {
        Optional<UserDTO> optionalUserDTO = userDao.userData(id);
        if(optionalUserDTO.isPresent())
		{
        	Set<RoleDTO> roleDTO = userDao.roleData(id);
            return UserPrincipal.create(optionalUserDTO.get(),roleDTO);
		}
        throw new ResourceNotFoundException("User not found with userid : " + id);
    }
}
