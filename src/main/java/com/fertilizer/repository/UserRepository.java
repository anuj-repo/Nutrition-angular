package com.fertilizer.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.enums.Status;
import com.fertilizer.enums.UserType;
import com.fertilizer.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	
	@Override
	Optional<User> findById(Long id);

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	boolean existsByUsernameAndStatus(String username, Status status);

	Optional<User> findByForgetPasswordToken(String token);

	boolean existsByUsernameAndStatusNot(String username, Status status);

	Optional<User> findByUsernameAndStatus(String username, Status status);

	Optional<User> findByEmail(String email);

	boolean existsByEmailAndStatusNot(String email, Status status);

	Optional<User> findByEmailAndStatusNot(String email, Status deleted);

	Optional<User> findByIdAndStatusNot(Long userId, Status deleted);

	Optional<User> findByUsernameAndUserType(String email, UserType userType);

	Optional<User> findByUsernameAndUserTypeAndStatus(String email, UserType userType, Status status);

	Optional<User> findByUsernameAndStatusAndUserType(String email, Status status, UserType userType);

	//Optional<User> findByClientId(Long long1);

	//List<User> findByAgencyIdAndStatus(String id, Status active);

	Optional<User> findByIdAndStatus(Long userId, Status active);

	List<User> findByUserType(UserType c);

	//Optional<User> findByAgencyIdAndUsername(String agencyId, String clientCompanyName);

	List<User> findByStatus(Status active);

	Optional<User> findByIdAndEmail(Long id, String email);

	List<User> findByPasswordResetLink(Date date);

//here
	Optional<User> findIdByReferralCode(String referralCode);

	boolean existsByEmail(String email);

	List<User> findAllByEmailIn(List<String> emails);
	
    //List<User> findByTotalAccountBalanceGreaterThan(Long balance);
    
    List<User> findByTotalAccountBalanceGreaterThan(BigInteger balance);


}
