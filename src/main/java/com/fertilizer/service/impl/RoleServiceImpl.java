package com.fertilizer.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fertilizer.enums.AddressType;
import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.EntityConstant;
import com.fertilizer.enums.Status;
import com.fertilizer.enums.UserType;
import com.fertilizer.exception.BadRequestException;
import com.fertilizer.exception.ResourceNotFoundException;
import com.fertilizer.exception.RoleNotFoundException;
import com.fertilizer.exception.UserNameAlredyExistException;
import com.fertilizer.messgeConstant.SfdcConstants;
import com.fertilizer.model.City;
import com.fertilizer.model.Country;
import com.fertilizer.model.Orders;
import com.fertilizer.model.Role;
import com.fertilizer.model.State;
import com.fertilizer.model.User;
import com.fertilizer.model.UserAddress;
import com.fertilizer.model.UserAuthToken;
import com.fertilizer.payload.response.UserDetailDTO;
import com.fertilizer.repository.CityRepository;
import com.fertilizer.repository.CountryRepository;
import com.fertilizer.repository.OrdersRepository;
import com.fertilizer.repository.RoleRepository;
import com.fertilizer.repository.StateRepository;
import com.fertilizer.repository.UserAuthTokenRepository;
import com.fertilizer.repository.UserRepository;
import com.fertilizer.request1.AddressInfo;
import com.fertilizer.request1.RoleBasedBillingUpdateUserDTO;
import com.fertilizer.request1.RoleBasedUpdateUserDTO;
import com.fertilizer.request1.RoleBasedUserCreateDTO;
import com.fertilizer.service.EmailLogsService;
import com.fertilizer.service.RoleService;
import com.fertilizer.util.MessageConstant;
import com.fertilizer.util.Response;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrdersRepository orderRepository;
	@Autowired
	private EmailLogsService emailLogsService;

	@Autowired
	private UserAuthTokenRepository userAuthTokenRepository;

	@Autowired
	private RoleRepository roleRepository;

//	@Autowired
//	private ClientsRepository clientsRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${sso.otpfor.signup}")
	private String signup;

	@Autowired
	private PasswordServiceImpl passwordServiceImpl;

	@Value("${rawPassword.prefix}")
	private String rawPasswordPrefix;

	@Value("${adworks.url}")
	private String adworksUrl;

	@Value("${verify.EXPIRE_MINS}")
	private Integer expiryMinutes;

	@Value("${verify.OTP.Length}")
	private Integer OTPLength;

	@Value("${verify.alphanumeric.Length}")
	private Integer alphanumericLength;

	private Predicate<Object> nullCheckPredicate;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private StateRepository stateRepository;

	private static final Logger logger = LogManager.getLogger(RoleServiceImpl.class);

	@PostConstruct
	void init() {
		nullCheckPredicate = Objects::nonNull;
	}

	@Override
	@Transactional
	public Response<String> registerUserInPortal(RoleBasedUserCreateDTO roleBasedUserCreateDTO, User loggedInUser) {

		logger.debug("saveRoleBasedUser method of RoleServiceImpl is called.");

		// check userName uniqueness
		User user = new User();
		checkUserNameUniqueness(roleBasedUserCreateDTO);
		validateUserType(loggedInUser);
		// userCreationLimitValidation(loggedInUser, roleBasedUserCreateDTO);
//		if (loggedInUser.getUserType().equals(UserType.A)) {
//			user.setOwnerId(loggedInUser.getOwnerId());
//		} else {
//			user.setOwnerId(loggedInUser.getId());
//		}
		user.setUsername(roleBasedUserCreateDTO.getEmail());
		user.setFname(roleBasedUserCreateDTO.getFirstName());
		user.setLname(roleBasedUserCreateDTO.getLastName());
		user.setEmail(roleBasedUserCreateDTO.getEmail());

		user.setUserType(UserType.A);
		if (roleBasedUserCreateDTO.getRole() != null) {
			roleBasedUserCreateDTO.setRole(roleBasedUserCreateDTO.getRole()
					.replaceFirst(EntityConstant.ROLE_PREAPPANDER_FOR_SECURITY.getName(), ""));

			Optional<Role> findByRoleIdentifier = roleRepository.findByRoleIdentifier(roleBasedUserCreateDTO.getRole());
			if (findByRoleIdentifier.isPresent()) {
				Role role = findByRoleIdentifier.get();
				user.setRole(role);
			} else {
				throw new RoleNotFoundException("given role name not exist");
			}
		} else {
			throw new RoleNotFoundException("Please provide role for the user");
		}
		Date linkGeneratedTime = new Date();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String alphanumericString = getAlphaNumericString(alphanumericLength);
		String firstFourDigit = String.valueOf(timestamp.getTime()).substring(0, 4);
		User userData = userRepository.save(user);
		String link = adworksUrl + "redirect-sub-user/registration/" + userData.getId() + "/" + alphanumericString + "/"
				+ firstFourDigit;
		userData.setPasswordResetLink(link);
		user.setPasswordChangeLog(
				"Link : " + link + " generated at : " + linkGeneratedTime + " by User : " + loggedInUser.getId());
		userData.setPasswordLinkGeneratedAt(linkGeneratedTime);
//		if (roleBasedUserCreateDTO.getClients() != null && !roleBasedUserCreateDTO.getClients().isEmpty()) {
//			String[] clientIds = roleBasedUserCreateDTO.getClients().split(",");
//			for (String clientId : clientIds) {
//				Optional<Clients> clientsOptional = clientsRepository.findById(Long.parseLong(clientId));
//				if (clientsOptional.isPresent()) {
//					Clients clients = clientsOptional.get();
//					userData.getClient().add(clients);
//				}
//			}
//			userData.setClient(userData.getClient());
//		}
		userRepository.save(userData);
		sendOnBoardingEmail(userData, link);
		return Response.ok("User details saved");
	}

	private void validateUserType(User loggedInUser) {
		if (loggedInUser.getUserType().equals(UserType.A)) {
			throw new BadRequestException("Sub Users Can Not Be Added ");
		}
	}

	private String getAlphaNumericString(Integer alphanumericLength) {

		StringBuilder sb = new StringBuilder(alphanumericLength);

		for (int i = 0; i < alphanumericLength; i++) {
			// generate a random number between 0 to AlphaNumericString variable length
			int index = (int) (SfdcConstants.alphaNumericString.length() * Math.random());
			// add Character one by one in end of sb
			sb.append(SfdcConstants.alphaNumericString.charAt(index));
		}
		return sb.toString();
	}

	private void sendOnBoardingEmail(User user, String link) {

	}

	private void checkUserNameUniqueness(RoleBasedUserCreateDTO roleBasedUserCreateDTO) {

		logger.debug("checkUserNameUniqueness method of RoleServiceImpl is called.");
		Optional<User> existsByUsername = userRepository.findByUsername(roleBasedUserCreateDTO.getEmail());
		if (existsByUsername.isPresent()) {
			throw new UserNameAlredyExistException(
					"You are already a part of Adworks, please log-in with your credential");
		}
		Optional<User> existsByEmail = userRepository.findByEmail(roleBasedUserCreateDTO.getEmail());
		if (existsByEmail.isPresent()) {
			throw new UserNameAlredyExistException(
					"You are already a part of Adworks, please log-in with your credential");
		}
	}

	@Override
	@Transactional
	public Response<String> resendSubUserOnboardingLink(User loggedInUser, Long subUserId) {
		Optional<User> optionalUser = userRepository.findById(subUserId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			Date linkGeneratedTime = new Date();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String alphanumericString = getAlphaNumericString(alphanumericLength);
			String firstFourDigit = String.valueOf(timestamp.getTime()).substring(0, 4);
			String link = adworksUrl + "redirect-sub-user/registration/" + user.getId() + "/" + alphanumericString + "/"
					+ firstFourDigit;
			user.setPasswordResetLink(link);
			String passwordLog = user.getPasswordChangeLog() + " and New Link : " + link + " generated at : "
					+ linkGeneratedTime + " by User : " + loggedInUser.getId();
			user.setPasswordChangeLog(passwordLog);
			user.setPasswordLinkGeneratedAt(linkGeneratedTime);
			userRepository.save(user);
			sendOnBoardingEmail(user, link);
			return Response.ok("Onboarding Mail sent successfully!!");
		} else {
			throw new ResourceNotFoundException("Sub User Details not found!!");
		}
	}

	private char[] generateUniqueNumber() {

		Random random = new Random();
		char[] otp = new char[OTPLength];
		String numbers = MessageConstant.NUMBERS.getName();

		for (int i = 0; i < OTPLength; i++) {
			otp[i] = numbers.charAt(random.nextInt(numbers.length()));
		}
		return otp;
	}


	@Override
	@Transactional
	public Response<UserDetailDTO> getSubUserRegistrationDetailById(Long userId) {
//		Optional<User> optionalUser = userRepository.findById(userId);
//		if (optionalUser.isPresent()) {
//			User user = optionalUser.get();
//			UserDetailDTO userDetailDTO = new UserDetailDTO();
//			userDetailDTO.setFname(user.getFname());
//			userDetailDTO.setLname(user.getLname());
//			userDetailDTO.setEmail(user.getEmail());
//
//			userDetailDTO.setUserType(user.getUserType());
//			userDetailDTO.setRoleIdentifier(user.getRole().getRoleIdentifier());
//
//			if (user.getClient() != null && !user.getClient().isEmpty())
//				userDetailDTO.setClients(user.getClient().stream().map(client -> String.valueOf(client.getId()))
//						.collect(Collectors.joining(",")));
//
//			// Fetch SuperUserDetails
////			Optional<User> optionalSuperUser = userRepository.findById(user.getOwnerId());
////			if (optionalSuperUser.isPresent()) {
////				User superUser = optionalSuperUser.get();
////				return Response.ok(userDetailDTO, "User details found successfully!!");
////			} else {
////				throw new ResourceNotFoundException("Super User Details not found!!");
////			}
//		} else {
	//		throw new ResourceNotFoundException("User Details not found!!");
	//	}
		return null;
	}

	@Override
	@Transactional
	public Response<String> updateSubUserProfile(RoleBasedUpdateUserDTO roleBasedUserCreateDTO, Long userId,
			HttpServletRequest request) {

		Optional<User> findByIdAndStatusNot = userRepository.findByIdAndStatusNot(userId, Status.DELETED);

		if (findByIdAndStatusNot.isPresent()) {
			boolean ssoFlag = true;
			User user = findByIdAndStatusNot.get();

			Long tokenId = passwordServiceImpl.getTokenId(request);
			Optional<UserAuthToken> findByTokenId = userAuthTokenRepository.findById(tokenId);
			if (findByTokenId.isPresent()) {
				UserAuthToken userAuthToken = findByTokenId.get();

			} else {
				throw new BadRequestException("Invalid token id.");
			}

			if (ssoFlag) {
				if (roleBasedUserCreateDTO.getFirstName() != null && !roleBasedUserCreateDTO.getFirstName().isEmpty()) {
					user.setFname(roleBasedUserCreateDTO.getFirstName());
				}
				if (roleBasedUserCreateDTO.getLastName() != null && !roleBasedUserCreateDTO.getLastName().isEmpty()) {
					user.setLname(roleBasedUserCreateDTO.getLastName());
				}
				if (roleBasedUserCreateDTO.getPhoneNumber() != null
						&& !roleBasedUserCreateDTO.getPhoneNumber().isEmpty()) {
					user.setMobileNumber(roleBasedUserCreateDTO.getPhoneNumber());
				}
				if (roleBasedUserCreateDTO.getBillingAddress() != null
						&& !roleBasedUserCreateDTO.getBillingAddress().isEmpty()) {
					user.setAddress(roleBasedUserCreateDTO.getBillingAddress());
				}
				if (roleBasedUserCreateDTO.getBillingCity() != null
						&& !roleBasedUserCreateDTO.getBillingCity().isEmpty()) {
					user.setAddress(roleBasedUserCreateDTO.getBillingCity());
				}

				if (roleBasedUserCreateDTO.getBillingState() != null
						&& !roleBasedUserCreateDTO.getBillingState().isEmpty()) {
					user.setAddress(roleBasedUserCreateDTO.getBillingState());
				}
				if (roleBasedUserCreateDTO.getBillingPinCode() != null
						&& !roleBasedUserCreateDTO.getBillingPinCode().isEmpty()) {
					user.setAddress(roleBasedUserCreateDTO.getBillingPinCode());
				}
				userRepository.save(user);
			} else {
				throw new BadRequestException("User Profile not updated.");
			}
		} else {
			throw new BadRequestException("User is not found.");
		}
		return Response.ok("User Profile Updated Successfully");
	}

	@Override
	@Transactional
	public Response<String> updateSubUserDetailsBySuperAdmin(RoleBasedUpdateUserDTO roleBasedUpdateUserDTO,
			Long userId) {

//		logger.debug("updateSubUserDetailsBySuperAdmin method of RoleServiceImpl is called.");
//		Optional<User> findByIdAndStatusNot = userRepository.findByIdAndStatusNot(userId, Status.DELETED);
//		if (findByIdAndStatusNot.isPresent()) {
//			User userDetails = findByIdAndStatusNot.get();
//			if (roleBasedUpdateUserDTO.getRole() != null) {
//				roleBasedUpdateUserDTO.setRole(roleBasedUpdateUserDTO.getRole()
//						.replaceFirst(EntityConstant.ROLE_PREAPPANDER_FOR_SECURITY.getName(), ""));
//				if (roleBasedUpdateUserDTO.getRole().equalsIgnoreCase("L1")) {
//					if (userDetails.getRole().getRoleIdentifier().equalsIgnoreCase(roleBasedUpdateUserDTO.getRole())) {
//						throw new BadRequestException("Role Can Not Be Updated To L1 As L1 User Is Already Present");
//					}
//				}
//				Optional<Role> findByRoleIdentifier = roleRepository
//						.findByRoleIdentifier(roleBasedUpdateUserDTO.getRole());
//				if (findByRoleIdentifier.isPresent()) {
//					Role role = findByRoleIdentifier.get();
//					userDetails.setRole(role);
//				} else {
//					throw new RoleNotFoundException("Given role name doesn't exist");
//				}
//			}
//
//			if (roleBasedUpdateUserDTO.getClients() != null) {
//				if (roleBasedUpdateUserDTO.getClients().isEmpty()) {
//					Set<Clients> userClients = userDetails.getClient();
//					userClients.clear();
//					userDetails.setClient(userClients);
//				} else {
//					String[] clientIds = roleBasedUpdateUserDTO.getClients().split(",");
//					userDetails.getClient().clear();
//					for (String clientId : clientIds) {
//						Optional<Clients> clientsOptional = clientsRepository.findById(Long.parseLong(clientId));
//						if (clientsOptional.isPresent()) {
//							Clients clients = clientsOptional.get();
//							userDetails.getClient().add(clients);
//						}
//					}
//					userDetails.setClient(userDetails.getClient());
//				}
//			}
//			userRepository.save(userDetails);
//		} else {
//			throw new UsernameNotFoundException("User Id Not Found");
//		}
		return Response.ok("User Updated Successfully");
	}

	public static long getDateDiff(final Date date1, final Date date2, final TimeUnit timeUnit) {
		long diffInMiles = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMiles, TimeUnit.MILLISECONDS);
	}

	@Override
	public String markUserStatus(Long userId, Status st) {
		Optional<User> userData = userRepository.findById(userId);
		if (userData.isPresent()) {
			User user = userData.get();
			user.setStatus(st);
			userRepository.save(user);
		}
		return st == Status.ACTIVE ? "You have successfully Activated User" : "You have successfully Deactivated User";
	}

	@Override
	public Integer getSubUserDetailsForOrderLimit(Long userId) {
		int orderCount = 0;
		List<Orders> orderDetails = orderRepository.findByUserIdAndStatus(userId, Status.ACTIVE);
		if (!orderDetails.isEmpty()) {
			for (Orders orders : orderDetails) {
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String currentDate = formatter.format(date);
				String creationDate = orders.getCreatedAt().toString().substring(0, 10);
				if (currentDate.equalsIgnoreCase(creationDate)) {
					orderCount++;
				}
			}
		}
		return orderCount;
	}

	@Override
	public String updateSubUserBillingProfile(RoleBasedBillingUpdateUserDTO roleBasedUserCreateDTO, User user) {
		try {
			UserAddress userAddress = new UserAddress();
			userAddress.setUser(user);
			userAddress.setAddress(roleBasedUserCreateDTO.getAddress());
			userAddress.setCity(roleBasedUserCreateDTO.getCity());
			userAddress.setState(roleBasedUserCreateDTO.getState());
			userAddress.setPincode(roleBasedUserCreateDTO.getPincode());
			userAddress.setId(roleBasedUserCreateDTO.getAddressId());
			userAddress.setIsDefault(BooleanEnum.YES);
			userAddress.setAddressType(AddressType.BILLING);

			user.setFname(roleBasedUserCreateDTO.getFirstName());
			user.setLname(roleBasedUserCreateDTO.getLastName());
			user.setMobileNumber(roleBasedUserCreateDTO.getPhoneNumber());

			AddressInfo billingInfo = new AddressInfo();
			billingInfo.setCity(userAddress.getCity());
			billingInfo.setState(userAddress.getState());
			if (nullCheckPredicate.test(billingInfo.getCity()) && !billingInfo.getCity().isEmpty()
					&& nullCheckPredicate.test(billingInfo.getState()) && !billingInfo.getState().isEmpty()) {
				setBillingAddress(userAddress, billingInfo);
			}
			List<UserAddress> address = new ArrayList<>();
			address.add(userAddress);
			user.setUserAddress(address);
			userRepository.save(user);
			return "Success";

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}

	private void setBillingAddress(UserAddress userAddress, AddressInfo addressInfo) {
		if (nullCheckPredicate.test(addressInfo.getState()) && !addressInfo.getState().isEmpty()) {
			Optional<State> optionalState = stateRepository.findByStateName(addressInfo.getState().trim());
			if (optionalState.isPresent()) {
				userAddress.setState(optionalState.get().getStateName());
				userAddress.setStateId(optionalState.get().getId());
			}
		} else {
			userAddress.setState(addressInfo.getState());
			userAddress.setStateId(null);
		}
		if (nullCheckPredicate.test(addressInfo.getCity()) && !addressInfo.getCity().isEmpty()) {

			Optional<City> optionalCity = cityRepository.findByCityName(addressInfo.getCity().trim());

			if (optionalCity.isPresent()) {
				userAddress.setCityId(optionalCity.get().getId());
				userAddress.setCity(optionalCity.get().getCityName());

			} else {
				City newCity = new City();
				newCity.setCityName(addressInfo.getCity().toLowerCase().trim());
				if (nullCheckPredicate.test(userAddress.getStateId())) {
					newCity.setStateId(userAddress.getStateId());
				}
				newCity.setCountryId(userAddress.getCountryId());

				City savedCity = cityRepository.save(newCity);
				userAddress.setCityId(savedCity.getId());
				userAddress.setCity(savedCity.getCityName());
				userAddress.setIsOtherCity(BooleanEnum.YES);
			}
		} else {
			userAddress.setCity(addressInfo.getCity());
			userAddress.setCityId(null);
		}

		Optional<Country> optionalCountry = countryRepository.findByCountryName("India");
		if (optionalCountry.isPresent()) {
			userAddress.setCountry(optionalCountry.get().getCountryName());
			userAddress.setCountryId(optionalCountry.get().getId());
		} else {
			throw new BadRequestException("Country not found.");
		}

	}

	@Override
	public void sendReminderEmailForSubUser() {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getSubUserDetailsForOrderEditLimit(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
