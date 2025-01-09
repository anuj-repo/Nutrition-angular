package com.fertilizer.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fertilizer.config.JwtTokenProvider;
import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.Source;
import com.fertilizer.enums.Status;
import com.fertilizer.exception.BadRequestException;
import com.fertilizer.exception.InconsistentDataException;
import com.fertilizer.exception.InvalidUserNameOrPasswordException;
import com.fertilizer.exception.RoleNotFoundException;
import com.fertilizer.exception.SSOException;
import com.fertilizer.model.Role;
import com.fertilizer.model.User;
import com.fertilizer.model.UserAuthToken;
import com.fertilizer.payload.response.JwtAuthenticationResponse;
import com.fertilizer.repository.RoleRepository;
import com.fertilizer.repository.UserAuthTokenRepository;
import com.fertilizer.repository.UserRepository;
import com.fertilizer.request1.LoginRequest;
import com.fertilizer.request1.UserCreateDto;
import com.fertilizer.service.AuthTokenService;
import com.fertilizer.service.EmailLogsService;
import com.fertilizer.service.UserService;
import com.fertilizer.util.Response;
import com.fertilizer.util.SSOResponseData;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserAuthTokenRepository userAuthTokenRepository;

	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	@Value("${app.jwtRefreshExpirationInMs}")
	private int jwtRefreshExpirationInMs;

	@Value("${sfdc.jwtExpirationInMs}")
	private int jwtSFDCExpirationInMs;

	@Value("${sfdc.jwtRefreshExpirationInMs}")
	private int jwtSFDCRefreshExpirationInMs;

	@Value("${nonstrict.admin.domains}")
	private String nonStrictAdminDomain;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	@Lazy
	private UserService userService;

	@Autowired
	private EmailLogsService emailLogsService;

	private static final Logger logger = LogManager.getLogger(AuthTokenServiceImpl.class);

	public List<String> createAuthToken(Long userId, Long tokenId, Date now) {
		logger.debug("createAuthToken method called in AuthTokenServiceImpl Service");
		List<String> response = new ArrayList<>(2);
		String authToken = tokenProvider.generateToken(userId, tokenId, now,
				new Date(now.getTime() + jwtExpirationInMs));
		String refreshToken = tokenProvider.generateToken(userId, tokenId, now,
				new Date(now.getTime() + jwtRefreshExpirationInMs));
		response.add(authToken);
		response.add(refreshToken);
		return response;
	}

	public List<String> createSFDCAuthToken(Long userId, Long tokenId, Date now) {
		logger.debug("createAuthToken method called in AuthTokenServiceImpl Service");
		List<String> response = new ArrayList<>(2);
		String authToken = tokenProvider.generateToken(userId, tokenId, now,
				new Date(now.getTime() + jwtSFDCExpirationInMs));
		String refreshToken = tokenProvider.generateToken(userId, tokenId, now,
				new Date(now.getTime() + jwtSFDCRefreshExpirationInMs));
		response.add(authToken);
		response.add(refreshToken);
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<Response<JwtAuthenticationResponse>> authenticateUser(LoginRequest loginRequest,
			HttpServletRequest request) {
		User user = null;

		String origin = request.getHeader("origin");
		logger.debug("authenticateUser method called in AuthTokenServiceImpl Service");

		Optional<User> userData = userRepository.findByUsername(loginRequest.getUsernameOrEmail());
		if (userData.isPresent()) {
			user = userData.get();
			if (user.getRole() != null && user.getRole().getRoleName().equalsIgnoreCase("Admin"))
				checkAdminDomain(origin);
			return loginWithoutSSO(loginRequest, false);
		}
		throw new SSOException("success", null);
	}

	private void checkAdminDomain(String origin) {
		logger.debug("checkAdminDomain method called in AuthTokenServiceImpl Service");
		if (origin != null && nonStrictAdminDomain != null) {
			List<String> urlsList = Arrays.asList(nonStrictAdminDomain.split(",")).stream()
					.collect(Collectors.toList());
			Boolean checkStrictUrl = urlsList.contains(origin);
			if (!checkStrictUrl) {
				throw new InvalidUserNameOrPasswordException("Invalid Credentials");
			}
		}
	}

	@Override
	public ResponseEntity<Response<JwtAuthenticationResponse>> loginWithoutPassword(User user, String message,
			String ssoToken) {
		if (null != user.getStatus() && !user.getStatus().equals(Status.ACTIVE)) {
			throw new InconsistentDataException("User is inactive/deleted. Please contact Admin");
		}
		return saveUserAuthToken(user, message, ssoToken);
	}

	public ResponseEntity<Response<JwtAuthenticationResponse>> loginWithoutSSO(LoginRequest loginRequest,
			Boolean isInternalRequest) {
		Optional<User> findByUsername = userRepository.findByUsername(loginRequest.getUsernameOrEmail());
		if (findByUsername.isPresent()) {
			User user = findByUsername.get();
//			if (null == user.getCompletedStep() || user.getCompletedStep() < 2) {
//				if (null == user.getCompletedStep())
//					user.setCompletedStep(1);
//				throw new IncompleteUserRegistrationException("Please complete your registration process.",
//						new SignupResponseDTO(user.getEmail(), user.getCompletedStep()));
//			} else 

			if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
				return loginWithoutPassword(user, "Loggedin successfully.", null);
			}
			throw new InvalidUserNameOrPasswordException("Invalid Username or Password.");
		}
		throw new InvalidUserNameOrPasswordException("This email is not registered with us. Please register!");
	}

	public Supplier<User> saveOterSSOUser(SSOResponseData data, LoginRequest loginRequest) {
		return () -> {
			User user = new User();
			user.setUsername(data.getEmail());
			user.setEmail(data.getEmail());
			user.setMobileNumber(data.getMobileNumber());
			user.setStatus(Status.ACTIVE);

			user.setFname(data.getName());
			user.setLname(data.getName());
			user.setIsPhoneVerified(BooleanEnum.NO);
			user.setIsDetailCompleted(BooleanEnum.NO);
			user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
			Optional<Role> findByRoleIdentifier = roleRepository.findByRoleIdentifier("User");
			if (findByRoleIdentifier.isPresent()) {
				Role role = findByRoleIdentifier.get();
				user.setRole(role);
			} else {
				throw new RoleNotFoundException("given role name not exist");
			}
			User saveUser = userRepository.save(user);

//			lead create on sfdc
			UserCreateDto userCreateDto = new UserCreateDto();
			userCreateDto.setFname(saveUser.getFname());

			userCreateDto.setCompName(saveUser.getFname());
			if (saveUser.getMobileNumber() != null && !saveUser.getMobileNumber().isEmpty()) {
				userCreateDto.setPhone1(saveUser.getMobileNumber());
			} else {
				userCreateDto.setPhone1("");
			}
			userCreateDto.setLname(saveUser.getLname());
			userCreateDto.setEmail(saveUser.getEmail());

			return saveUser;
		};
	}

	public ResponseEntity<Response<JwtAuthenticationResponse>> saveUserAuthToken(User user, String message,
			String ssoAuthToken) {
		UserAuthToken userAuthToken = new UserAuthToken();
		userAuthToken.setUserId(user.getId());
		userAuthToken.setLoggedInFromSource(Source.AD);
		userAuthToken.setIsExpired(BooleanEnum.NO);

		Date now = new Date();
		UserAuthToken savedUserAuthToken = userAuthTokenRepository.save(userAuthToken);
		List<String> jwt = createSFDCAuthToken(user.getId(), savedUserAuthToken.getId(), now);
		if (!jwt.isEmpty() && jwt.size() == 2) {
			logger.debug("saveUserAuthToken method called in AuthTokenServiceImpl Service");
			Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),
					user.getPassword());
			SecurityContextHolder.getContext().setAuthentication(authentication);

			savedUserAuthToken.setExpiresAt(new Date(now.getTime() + jwtExpirationInMs));
			savedUserAuthToken.setRefreshToken(jwt.get(1));
			savedUserAuthToken.setAuthToken(jwt.get(0));
			savedUserAuthToken.setSsoAuthToken(ssoAuthToken);

			return ResponseEntity.ok(Response.ok(new JwtAuthenticationResponse(jwt.get(0), jwt.get(1)), message));
		}
		throw new InvalidUserNameOrPasswordException("Failed to create Authtoken.Please try after some time");
	}

	@Override
	@Transactional
	public ResponseEntity<Response<Object>> expireUserAuthToken(long tokenId) {
		logger.debug("expireUserAuthToken method called for signout endpoint");
		Optional<UserAuthToken> resultset = userAuthTokenRepository.findById(tokenId);
		if (resultset.isPresent()) {
			UserAuthToken userAuthToken = resultset.get();
			if (userAuthToken.getIsExpired() != null && userAuthToken.getIsExpired().equals(BooleanEnum.YES)) {
				return ResponseEntity.ok(Response.ok("Logged out successfully."));
			}
			userAuthToken.setIsExpired(BooleanEnum.YES);
			userAuthToken.setLoggedOutAt(new Date());
			return ResponseEntity.ok(Response.ok("Logged out successfully."));
		}
		throw new BadRequestException("unable to logout.");
	}

}