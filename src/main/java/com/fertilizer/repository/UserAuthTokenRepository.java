package com.fertilizer.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.model.UserAuthToken;

/**
 * @author Dhiraj
 *
 */
@Repository
public interface UserAuthTokenRepository extends JpaRepository<UserAuthToken, Long> {

	UserAuthToken findTopByUserIdAndIsExpiredAndLoggedOutAtOrderByExpiresAtDesc(Long userId, BooleanEnum isExpired, Date loggedOutAt);
	UserAuthToken findTopByUserIdOrderByExpiresAtDesc(Long id);
}
