package com.fertilizer.service.impl;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fertilizer.DTO.EditProfileRequestDTO;
import com.fertilizer.DTO.UserHierarchyDTO;
import com.fertilizer.dao.UserDao;
import com.fertilizer.enums.AddressType;
import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.PackageTakenEnum;
import com.fertilizer.enums.Status;
import com.fertilizer.enums.UserLevel;
import com.fertilizer.enums.UserType;
import com.fertilizer.exception.BadRequestException;
import com.fertilizer.exception.RoleNotFoundException;
import com.fertilizer.exception.UserNameAlredyExistException;
import com.fertilizer.model.City;
import com.fertilizer.model.Country;
import com.fertilizer.model.Permission;
import com.fertilizer.model.RegistrationRequestResponse;
import com.fertilizer.model.Role;
import com.fertilizer.model.State;
import com.fertilizer.model.User;
import com.fertilizer.model.UserAddress;
import com.fertilizer.payload.response.UserAddressDTO;
import com.fertilizer.payload.response.UserProfileDetailDTO;
import com.fertilizer.repository.CityRepository;
import com.fertilizer.repository.CountryRepository;
import com.fertilizer.repository.PermissionRepository;
import com.fertilizer.repository.RegistrationRequestResponseRepository;
import com.fertilizer.repository.RoleRepository;
import com.fertilizer.repository.StateRepository;
import com.fertilizer.repository.UserAddressRepository;
import com.fertilizer.repository.UserAuthTokenRepository;
import com.fertilizer.repository.UserRepository;
import com.fertilizer.request.AdminUserSearch;
import com.fertilizer.request.SignUpRequest;
import com.fertilizer.request.UpdatePaymentPayload;
import com.fertilizer.request1.AddressInfo;
import com.fertilizer.request1.UserCreateDto;
import com.fertilizer.request1.UserUpdate;
import com.fertilizer.service.AuthTokenService;
import com.fertilizer.service.CommissionDistributionServiceForPackage1;
import com.fertilizer.service.EmailLogsService;
import com.fertilizer.service.RoleService;
import com.fertilizer.service.UserService;
import com.fertilizer.util.CommonUtils;
import com.fertilizer.util.MessageConstant;
import com.fertilizer.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private EmailLogsService emailLogsService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	private Predicate<Object> nullCheckPredicate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${role.user}")
	private String roleUser;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Lazy
	@Autowired
	private AuthTokenService authTokenService;

	@Autowired
	private PasswordServiceImpl passwordServiceImpl;

	@Autowired
	private UserAuthTokenRepository userAuthTokenRepository;

	@Autowired
	private UserAddressRepository userAddressRepository;

//	@Autowired
//	private ClientsRepository clientsRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	CommonUtils commonUtils;

//	@Autowired
//	CommissionDistributionServiceForPackage1 commissionDistributionServiceForPackage1;

	@Autowired
	CommissionDistributionServiceForPackage1ServiceImpl commissionDistributionServiceForPackage1ServiceImpl;

	@Autowired
	CommissionDistributionServiceForPackage2ServiceImpl commissionDistributionServiceForPackage2ServiceImpl;

	@Autowired
	CommissionDistributionServiceForPackage3ServiceImpl commissionDistributionServiceForPackage3ServiceImpl;

	@Autowired
	RegistrationRequestResponseRepository registrationRequestResponseRepository;

	@Value("${rawPassword.prefix}")
	private String rawPasswordPrefix;

	@Value("${default.referral}")
	private String defaultReferral;

	@Autowired
	EmailService emailService;

	@PostConstruct
	void init() {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		nullCheckPredicate = Objects::nonNull;
	}

//	public User registerNewUser(User user) {
//		Role role = roleRepository.findById(user.getId()).get();
//		// Set<Role> userRoles = new HashSet<>();
//		// userRoles.add(role);
//		user.setRole(role);
//		user.setUserPassword(getEncodedPassword(user.getUserPassword()));
//
//		return userRepository.save(user);
//	}

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	@Transactional
	public ResponseEntity<Response<String>> signUpUser(SignUpRequest payload) {
		User user = new User();
		String referralCode = defaultReferral;
		try {
			user.setFname(payload.getFirstName());
			user.setLname(payload.getLastName());
			user.setMobileNumber(payload.getMobNumber());
			if (checkEmailExist(payload.getEmail())) {
				return new ResponseEntity<>(new Response<>("Email already exist"), HttpStatus.BAD_REQUEST);
			}
			user.setEmail(payload.getEmail());
			user.setUserPassword(payload.getPassword());
			user.setGender(payload.getGender());
			String rawPassword = Base64.getEncoder()
					.encodeToString((rawPasswordPrefix + payload.getPassword()).getBytes("utf-8"));
			user.setRawPassword(rawPassword);
			user.setPassword(passwordEncoder.encode(payload.getPassword()));
			setUserRole(user);
			user.setUserLevel(UserLevel.BEGINNER);
			user.setStatus(Status.INACTIVE);
			user.setUsername(payload.getEmail());
			user.setIsDetailCompleted(BooleanEnum.YES);
			user.setAddress(payload.getAddress());
			user.setPincode(payload.getZipCode());
			user.setDob(Instant.parse(payload.getDob()).atZone(ZoneId.systemDefault()).toLocalDate());
			// user.setUpiId(payload.getUpiId());
			user.setPackageTaken(payload.getPackageTaken());
			user.setProductChoice(payload.getProductChoice());
			// user.setUserImage(payload.getUploadPhoto());
			List<UserAddress> address = new ArrayList<>();
			UserAddress userAddress = new UserAddress();
			userAddress.setAddress(payload.getAddress());
			userAddress.setAddressType(AddressType.BILLING);
			userAddress.setIsDefault(BooleanEnum.YES);
			userAddress.setPincode(payload.getZipCode());
			userAddress.setStatus(Status.ACTIVE);
			String cityName = cityRepository.findById(payload.getCity()).get().getCityName();
			String stateName = stateRepository.findById(payload.getState()).get().getStateName();
			user.setCity(cityName);
			user.setState(stateName);
			user.setCountry("India");
			userAddress.setCountry("India");
			userAddress.setCity(cityName);
			userAddress.setState(stateName);
			userAddress.setUser(user);
			address.add(userAddress);
			user.setUserAddress(address);
			if (payload.getReferralCode() != null && !payload.getReferralCode().isEmpty()) {
				referralCode = payload.getReferralCode();
			}
			user = setReferralUser(user, referralCode);
			userRepository.save(user);
			// sendEmail(user);
		} catch (Exception e) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(payload);
				RegistrationRequestResponse registrationRequestResponse = new RegistrationRequestResponse();
				registrationRequestResponse.setRequest(json);
				registrationRequestResponse.setResponse(e.toString());
				registrationRequestResponseRepository.save(registrationRequestResponse);
			} catch (JsonProcessingException e1) {
				System.out.println(e);
			}

		}
		return ResponseEntity.ok(Response.ok("User Added Successfully."));
	}

	private boolean checkEmailExist(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent())
			return true;
		return false;
	}

	private void sendEmail(User user) {
		emailService.registrationEmail(user);
	}

	public User setReferralUser(User user, String referralCode) {
		try {
			User parentUser = null;
			Optional<User> referedUser = userRepository.findIdByReferralCode(referralCode);
			if (referedUser.isPresent()) {
				parentUser = referedUser.get();
			} else {
				Optional<User> referedUser1 = userRepository.findIdByReferralCode(defaultReferral);
				parentUser = referedUser1.get();
			}
			user.setParent(parentUser);
			user.setUserType(UserType.C);
			user.setReferralCodeUsed(referralCode);
			user.setReferralCode(getReferalCode());
			return user;
		} catch (Exception e) {
			System.out.println(e);
		}
		return user;
	}

	@Override
	@Transactional
	public ResponseEntity<Response<String>> updateUserProfile(User user, EditProfileRequestDTO payload) {
		logger.debug("updateUserProfile method of UserServiceImpl is called.");

		user.setFname(payload.getFname());
		user.setLname(payload.getLname());
		user.setMobileNumber(payload.getMobileNumber());
		user.setAddress(payload.getAddress());
		user.setCity(payload.getCity());
		user.setState(payload.getState());
		user.setCountry(payload.getCountry());
		user.setPincode(payload.getPincode());
		userRepository.save(user);
		return ResponseEntity.ok(Response.ok("Profile Updated Successfully"));
	}

	private String getReferalCode() {
		int length = 6;
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder randomString = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			randomString.append(characters.charAt(random.nextInt(characters.length())));
		}

		return randomString.toString().toUpperCase();
	}

	@Override
	@Transactional(readOnly = true)
	public void checkUserNameUniqueness(UserCreateDto userCreateDto) {
		logger.debug("checkUserNameUniqueness method of UserServiceImpl is called.");
		Optional<User> existsByUsernameAndStatus = userRepository.findByUsername(userCreateDto.getEmail());
		if (existsByUsernameAndStatus.isPresent()) {
			User user = existsByUsernameAndStatus.get();
			throw new UserNameAlredyExistException(
					"You are already a part of Adworks, please log-in with your credential");
		}
	}

	public void checkClientNameUniqueness(UserCreateDto userCreateDto) {
		logger.debug("checkClientNameUniqueness method of UserServiceImpl is called.");
		Optional<User> existsByUsernameAndStatus = userRepository.findByUsername(userCreateDto.getEmail());
		if (existsByUsernameAndStatus.isPresent()) {
			throw new UserNameAlredyExistException("You are already a part of Adworks");
		}
	}

	public void checkClientNameUniquenessByCompName(String compaName) {
		logger.debug("checkClientNameUniqueness method of UserServiceImpl is called.");
		Optional<User> existsByUsernameAndStatus = userRepository.findByUsername(compaName);
		if (existsByUsernameAndStatus.isPresent()) {
			throw new UserNameAlredyExistException("You are already a part of Adworks");
		}
	}

	public void setUserRole(User user) {
		logger.debug("setUserRole method of UserServiceImpl is called.");
		Optional<Role> findByRoleIdentifier = roleRepository.findByRoleIdentifier(roleUser);
		if (findByRoleIdentifier.isPresent()) {
			Role role = findByRoleIdentifier.get();
			user.setRole(role);
		} else {
			throw new RoleNotFoundException("given role name not exist");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public User getSignedInUserDTO() {
		logger.debug("getSignedInUser method of UserServiceImpl is called.");
		User user = userDao.getUserMe();
		Hibernate.initialize(user.getChildren());
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public UserProfileDetailDTO getUserProfileDetailDTO(String username) {
		logger.debug("getUserProfileDetailDTO method of UserServiceImpl is called.");
		Optional<UserProfileDetailDTO> optionalUserProfileDetailDTO = userDao.getUserProfileDetailDTO(username);
		if (optionalUserProfileDetailDTO.isPresent()) {
			UserProfileDetailDTO userProfileDetailDTO = optionalUserProfileDetailDTO.get();
			User user = new User();
			user.setId(userProfileDetailDTO.getId());
			userProfileDetailDTO.setUserAddressDTO((userAddressRepository.findByUser(user)).stream()
					.map(userAddress -> modelMapper.map(userAddress, UserAddressDTO.class))
					.collect(Collectors.toList()));

			user.setId(userProfileDetailDTO.getId());
//			if (nullCheckPredicate.test(userProfileDetailDTO.getUserImageUrl())) {
//				String userLogo = AmazonClient.getCloudfrontPublicUrlWithoutSubFolder(cloudFrontAssetUrl,
//						profileLogoFolderName, userProfileDetailDTO.getUserImageUrl());
//				userProfileDetailDTO.setUserImageUrl(userLogo);
//			}

			List<Permission> permissionList = null;

			if (nullCheckPredicate.test(userProfileDetailDTO.getRoleId())) {
				permissionList = permissionRepository.findRolePermissions(userProfileDetailDTO.getRoleId());
				userProfileDetailDTO.setPermissions(permissionList.stream()
						.collect(Collectors.toMap(Permission::getName, permission -> permission)));
			}

			return userProfileDetailDTO;
		}
		throw new BadRequestException(MessageConstant.USER_DETAILS_NOT_FOUND);
	}

	@Override
	@Transactional
	public UserProfileDetailDTO updateUserProfileDetail(UserUpdate userUpdate, HttpServletRequest request,
			Boolean isUpdateByAdmin) {
		Optional<User> findByIdAndStatusNot = userRepository
				.findByIdAndStatusNot(Long.parseLong(userUpdate.getUserId()), Status.DELETED);
		if (findByIdAndStatusNot.isPresent()) {
			User user = findByIdAndStatusNot.get();
			boolean isUserDetailCompleted = true;
			if (user.getIsDetailCompleted() != null && user.getIsDetailCompleted().equals(BooleanEnum.NO)) {
				isUserDetailCompleted = false;
				if (!nullCheckPredicate.test(userUpdate.getUserType())) {
					throw new BadRequestException("Advertising Agency/Company is required");
				} else {
					user.setUserType(userUpdate.getUserType());
				}
			}

			Boolean isDirectUser = user.getUserType().equals(UserType.A);

			if (nullCheckPredicate.test(userUpdate.getFname()) && (isDirectUser || isUpdateByAdmin)) {
				user.setFname(userUpdate.getFname());
			}
			if (nullCheckPredicate.test(userUpdate.getLname()) && (isDirectUser || isUpdateByAdmin)) {
				user.setLname(userUpdate.getLname());
			}
//				if (!userUpdate.getPhone1().equals(user.getPhone1()) && (isDirectUser || isUpdateByAdmin)) {
//					user.setIsPhoneVerified(BooleanEnum.NO);
//				}
//				if (nullCheckPredicate.test(userUpdate.getPhone1()) && (isDirectUser || isUpdateByAdmin)) {
//					user.setPhone1(userUpdate.getPhone1());
//				}

			if (nullCheckPredicate.test(userUpdate.getAddress())) {
				user.setAddress(userUpdate.getAddress());
			}
			if (nullCheckPredicate.test(userUpdate.getPanNumber()) && !isUserDetailCompleted) {
				// user.setPanNumber(userUpdate.getPanNumber());
			}
			if (nullCheckPredicate.test(userUpdate.getPincode())) {
				user.setPincode(userUpdate.getPincode());
			}

			AddressInfo addressInfo = new AddressInfo();
			if (nullCheckPredicate.test(userUpdate.getCity())) {
				addressInfo.setCity(userUpdate.getCity());
			} else {
				addressInfo.setCity(user.getCity());
			}
			if (nullCheckPredicate.test(userUpdate.getState())) {
				addressInfo.setState(userUpdate.getState());
			} else {
				addressInfo.setState(user.getState());
			}

			if ((nullCheckPredicate.test(addressInfo.getCity()) && !addressInfo.getCity().isEmpty())
					|| (nullCheckPredicate.test(addressInfo.getState()) && !addressInfo.getState().isEmpty())) {
				setUserAddress(user, addressInfo);
			}
			if (nullCheckPredicate.test(userUpdate.getUserAddressDTO())) {
				List<UserAddress> userAddress = (userUpdate.getUserAddressDTO()).stream()
						.map(userObject -> modelMapper.map(userObject, UserAddress.class)).collect(Collectors.toList());
				userAddress.forEach(billingAddress -> {
					billingAddress.setUser(user);
					AddressInfo billingInfo = new AddressInfo();
					billingInfo.setCity(billingAddress.getCity());
					billingInfo.setState(billingAddress.getState());
					if (nullCheckPredicate.test(billingInfo.getCity()) && !billingInfo.getCity().isEmpty()
							&& nullCheckPredicate.test(billingInfo.getState()) && !billingInfo.getState().isEmpty()) {
						setBillingAddress(billingAddress, billingInfo);
					}
				});
				user.setUserAddress(userAddress);
			}
			User fetchUser = userRepository.save(user);
			return getUserProfileDetailDTO(fetchUser.getUsername());

		} else {
			throw new BadRequestException("User Profile not updated.");
		}
	}

	public void setBillingAddress(UserAddress userAddress, AddressInfo addressInfo) {
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

	public void setUserAddress(User fetchedUser, AddressInfo addressInfo) {

		if (nullCheckPredicate.test(addressInfo.getState()) && !addressInfo.getState().isEmpty()) {
			Optional<State> optionalState = stateRepository.findByStateName(addressInfo.getState().trim());
			if (optionalState.isPresent()) {
				fetchedUser.setState(optionalState.get().getStateName());

			}
		} else {
			fetchedUser.setState(addressInfo.getState());

		}

		if (nullCheckPredicate.test(addressInfo.getCity()) && !addressInfo.getCity().isEmpty()) {

			Optional<City> optionalCity = cityRepository.findByCityName(addressInfo.getCity().trim());

			if (optionalCity.isPresent()) {

				fetchedUser.setCity(optionalCity.get().getCityName());

			} else {
				City newCity = new City();
				newCity.setCityName(addressInfo.getCity().toLowerCase().trim());

				City savedCity = cityRepository.save(newCity);
				fetchedUser.setCity(savedCity.getCityName());

			}
		} else {
			fetchedUser.setCity(addressInfo.getCity());

		}

		Optional<Country> optionalCountry = countryRepository.findByCountryName("India");
		if (optionalCountry.isPresent()) {
			fetchedUser.setCountry(optionalCountry.get().getCountryName());

		} else {
			throw new BadRequestException("Country not found.");
		}
	}

//	@Override
//	public List<UserHierarchyDTO> findAllTeam(userRequestDTO) {
//		
//		List<User> user=userDao.getAllTeam(userRequestDTO);
//        List<UserHierarchyDTO> hierarchy = buildUserHierarchyForAdmin(userList);
//return hierarchy;
//		
//	}

	@Override
	public List<UserHierarchyDTO> findAllTeam(AdminUserSearch userRequestDTO) {
		List<User> users = userDao.getAllTeam(userRequestDTO);
		List<UserHierarchyDTO> hierarchy = buildUserHierarchyForAdmin(users);
		return hierarchy;
	}

	@Override
	public List<UserHierarchyDTO> buildUserHierarchyForAdmin(List<User> users) {
		if (users == null || users.isEmpty())
			return Collections.emptyList();
		List<UserHierarchyDTO> hierarchyDTOs = new ArrayList<>();
		for (User user : users) {
			UserHierarchyDTO hierarchyDTO = new UserHierarchyDTO(user);
			List<UserHierarchyDTO> childDTOs = buildUserHierarchyForAdmin(user.getChildren());
			hierarchyDTO.setChildren(childDTOs);
			hierarchyDTOs.add(hierarchyDTO);
		}

		return hierarchyDTOs;
	}

	@Override
	public UserHierarchyDTO buildUserHierarchy(User rootUser) {
		if (rootUser == null)
			return null;
		UserHierarchyDTO hierarchyDTO = new UserHierarchyDTO(rootUser);
		List<UserHierarchyDTO> childDTOs = rootUser.getChildren().stream().map(this::buildUserHierarchy)
				.collect(Collectors.toList());
		hierarchyDTO.setChildren(childDTOs);
		return hierarchyDTO;
	}

	public boolean checkEmailExists(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public ResponseEntity<Response<String>> reRegister() {
		commissionDistributionServiceForPackage1ServiceImpl.reRegisterUser();
		return null;
	}

	@Override
	public ResponseEntity<Response<String>> activateUser(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			User userDetails = user.get();
			userDetails.setStatus(Status.ACTIVE);
		}
		return ResponseEntity.ok(Response.ok("User Added Successfully."));

	}

	@Override
	public ResponseEntity<Response<String>> updatePayment(UpdatePaymentPayload payload) {
		Optional<User> user = userRepository.findById(payload.getUserId());
		if (user.isPresent()) {
			User userDetails = user.get();
			if (payload.getAmount() != null && payload.getAmount().equalsIgnoreCase("1000")) {
				commissionDistributionServiceForPackage1ServiceImpl.distributeRegistrationFee(userDetails);
			}
			if (payload.getAmount() != null && payload.getAmount().equalsIgnoreCase("2500")) {
				commissionDistributionServiceForPackage2ServiceImpl.distributeRegistrationFee(userDetails);
			}
			if (payload.getAmount() != null && payload.getAmount().equalsIgnoreCase("5000")) {
				commissionDistributionServiceForPackage3ServiceImpl.distributeRegistrationFee(userDetails);
			}
			userDetails.setStatus(Status.ACTIVE);
			userRepository.save(userDetails);
		}
		return ResponseEntity.ok(Response.ok("User Successfully Activated abd comission distributed"));
	}
}