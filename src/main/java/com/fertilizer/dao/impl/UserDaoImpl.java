package com.fertilizer.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fertilizer.dao.UserDao;
import com.fertilizer.enums.EntityConstant;
import com.fertilizer.enums.Status;
import com.fertilizer.exception.BadRequestException;
import com.fertilizer.exception.ResourceNotFoundException;
import com.fertilizer.model.Role;
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
import com.fertilizer.util.QueryUtil;

@Repository
public class UserDaoImpl implements UserDao {

	private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public Optional<UserDTO> userData(Long userId) {
		logger.debug("getUserData method in userDao using userId.");
		try {
			CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDTO> query = queryBuilder.createQuery(UserDTO.class);
			Root<User> queryRoot = query.from(User.class);

			query.select(queryBuilder.construct(UserDTO.class, queryRoot.get(EntityConstant.ID.getName()),
					queryRoot.get(EntityConstant.USERNAME.getName()), queryRoot.get(EntityConstant.USERTYPE.getName()),
					queryRoot.get(EntityConstant.FNAME.getName()), queryRoot.get(EntityConstant.LNAME.getName()),
					queryRoot.get(EntityConstant.EMAIL.getName()), queryRoot.get(EntityConstant.STATUS.getName())));

			List<Predicate> predicates = getPredicateByIdandStatus(userId, queryBuilder, queryRoot);
			query.where(queryBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
			TypedQuery<UserDTO> typedQuery = entityManager.createQuery(query);
			UserDTO userDTO = typedQuery.getSingleResult();
			return Optional.of(userDTO);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Failed to fetch user info");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Set<RoleDTO> roleData(Long userId) {
		logger.debug("getRoleData method in userDao using userId.");
		try {
			CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<RoleDTO> query = queryBuilder.createQuery(RoleDTO.class);
			Root<User> queryRoot = query.from(User.class);
			Join<User, Role> userRoles = queryRoot.join(EntityConstant.ROLE.getName());
			query.select(queryBuilder.construct(RoleDTO.class, userRoles.get(EntityConstant.ROLENAME.getName()),
					userRoles.get(EntityConstant.ROLEIDENTIFIER.getName()),
					userRoles.get(EntityConstant.ID.getName())));
			List<Predicate> predicates = getPredicateByIdandStatus(userId, queryBuilder, queryRoot);
			query.where(queryBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
			TypedQuery<RoleDTO> typedQuery = entityManager.createQuery(query);
			return new HashSet<>(typedQuery.getResultList());
		} catch (Exception e) {
			throw new ResourceNotFoundException("Failed to fetch role info");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UserDTO> userDataByUserName(String userName) {
		logger.debug("getUserData method in userDao using userName.");
		try {
			CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDTO> query = queryBuilder.createQuery(UserDTO.class);
			Root<User> queryRoot = query.from(User.class);

			query.select(queryBuilder.construct(UserDTO.class, queryRoot.get(EntityConstant.ID.getName()),
					queryRoot.get(EntityConstant.USERNAME.getName()), queryRoot.get(EntityConstant.USERTYPE.getName()),
					queryRoot.get(EntityConstant.FNAME.getName()), queryRoot.get(EntityConstant.LNAME.getName()),
					queryRoot.get(EntityConstant.EMAIL.getName()), queryRoot.get(EntityConstant.STATUS.getName())));

			List<Predicate> predicates = getPredicateforUserTypeAndUsername(userName, queryBuilder, queryRoot);
			query.where(queryBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
			TypedQuery<UserDTO> typedQuery = entityManager.createQuery(query);
			UserDTO userDTO = typedQuery.getSingleResult();
			return Optional.of(userDTO);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Failed to fetch user info using username");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Set<RoleDTO> roleDataByUserName(String userName) {
		logger.debug("getRoleData method in userDao using userName.");
		try {
			CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<RoleDTO> query = queryBuilder.createQuery(RoleDTO.class);
			Root<User> queryRoot = query.from(User.class);
			Join<User, Role> userRoles = queryRoot.join(EntityConstant.ROLES.getName());
			query.select(queryBuilder.construct(RoleDTO.class, userRoles.get(EntityConstant.ROLENAME.getName()),
					userRoles.get(EntityConstant.ROLEIDENTIFIER.getName()),
					userRoles.get(EntityConstant.ID.getName())));
			List<Predicate> predicates = getPredicateforUserTypeAndUsername(userName, queryBuilder, queryRoot);
			query.where(queryBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
			TypedQuery<RoleDTO> typedQuery = entityManager.createQuery(query);
			return new HashSet<>(typedQuery.getResultList());
		} catch (Exception e) {
			throw new ResourceNotFoundException("Failed to fetch role info using username");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserMe() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (username.equals(EntityConstant.ANONYMOUSUSER.getName())) {
			return null;
		}
		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = queryBuilder.createQuery(User.class);
		Root<User> queryRoot = query.from(User.class);
		List<Predicate> predicates = getPredicateforUserTypeAndUsername(username, queryBuilder, queryRoot);
		query.where(queryBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
		TypedQuery<User> typedQuery = entityManager.createQuery(query);
		try {
			return typedQuery.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new BadRequestException("User data not found");
		}
	}

	@Override
	public Optional<UserProfileDetailDTO> getUserProfileDetailDTO(String username) {
		logger.debug("getUserProfileDetailDTO Method Called in UserDao.");
		try {
			CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserProfileDetailDTO> query = queryBuilder.createQuery(UserProfileDetailDTO.class);
			Root<User> queryRoot = query.from(User.class);
			// Root<AgencyClients> agencyClientsRoot = query.from(AgencyClients.class);
			Join<User, Role> roleJoin = queryRoot.join(EntityConstant.ROLE.getName());
			query.select(queryBuilder.construct(UserProfileDetailDTO.class, queryRoot.get(EntityConstant.ID.getName()),
					queryRoot.get("parentId"), queryRoot.get(EntityConstant.EMAIL.getName()),
					queryRoot.get(EntityConstant.ADDRESS.getName()), queryRoot.get(EntityConstant.USERIMAGE.getName()),
					roleJoin.get("id"), roleJoin.get(EntityConstant.ROLEIDENTIFIER.getName()),
					roleJoin.get("parentRoleId"),

					queryRoot.get(EntityConstant.SALUTATION.getName()), queryRoot.get(EntityConstant.FNAME.getName()),
					queryRoot.get(EntityConstant.LNAME.getName()), queryRoot.get("referral_code"),
					queryRoot.get("referral_code_used"), queryRoot.get(EntityConstant.DESIGNATION.getName()),
					queryRoot.get(EntityConstant.PANNUMBER.getName()), queryRoot.get(EntityConstant.CITY.getName()),
					queryRoot.get(EntityConstant.STATE.getName()), queryRoot.get(EntityConstant.PINCODE.getName()),
					queryRoot.get(EntityConstant.USERIMAGE.getName()),
					queryRoot.get(EntityConstant.ISDETAILCOMPLETED.getName()),
					queryRoot.get(EntityConstant.USERTYPE.getName()),
					queryRoot.get(EntityConstant.ISPHONEVERIFIED.getName())));
			List<Predicate> predicates = getPredicateforUserTypeAndUsername(username, queryBuilder, queryRoot);

			query.where(queryBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
			TypedQuery<UserProfileDetailDTO> typedQuery = entityManager.createQuery(query);

			return Optional.of(typedQuery.getSingleResult());
		} catch (Exception e) {
			logger.catching(e);
			return Optional.empty();
		}
	}

	public List<Predicate> getPredicateforUserTypeAndUsername(String username, CriteriaBuilder queryBuilder,
			Root<User> queryRoot) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(queryBuilder.equal(queryRoot.get(EntityConstant.USERNAME.getName()), username));
		predicates.add(queryBuilder.equal(queryRoot.get(EntityConstant.STATUS.getName()), Status.ACTIVE));
		return predicates;
	}

	@Override
	public Optional<UserDetailDTO> getUserDetailDTO(Long id) {
		logger.debug("getUserDetailDTO Method Called in UserDao.");
		try {
			CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailDTO> query = queryBuilder.createQuery(UserDetailDTO.class);
			Root<User> queryRoot = query.from(User.class);

			query.select(queryBuilder.construct(UserDetailDTO.class, queryRoot.get(EntityConstant.ID.getName()),
					queryRoot.get(EntityConstant.EMAIL.getName()), queryRoot.get(EntityConstant.PHONE1.getName()),
					queryRoot.get(EntityConstant.COMPNAME.getName()), queryRoot.get(EntityConstant.ADDRESS.getName()),
					queryRoot.get(EntityConstant.SALUTATION.getName()), queryRoot.get(EntityConstant.FNAME.getName()),
					queryRoot.get(EntityConstant.LNAME.getName()), queryRoot.get(EntityConstant.ROLE.getName()),
					queryRoot.get(EntityConstant.COMPANYSIZE.getName()),
					queryRoot.get(EntityConstant.COMPANYTURNOVER.getName()),
					queryRoot.get(EntityConstant.DESIGNATION.getName()),
					queryRoot.get(EntityConstant.COMPNAME.getName()),
					queryRoot.get(EntityConstant.BUSINESSCATEGORY.getName()),
					queryRoot.get(EntityConstant.BUSINESSCATEGORYOTHER.getName()),
					queryRoot.get(EntityConstant.HASADVERTISEDBEFORE.getName()),
					queryRoot.get(EntityConstant.ADVERTISEDIN.getName()),
					queryRoot.get(EntityConstant.ADVERTISEDOTHERIN.getName()),
					queryRoot.get(EntityConstant.RECEIVENOTIFICATION.getName()),
					queryRoot.get(EntityConstant.GSTNUMBER.getName()),
					queryRoot.get(EntityConstant.PANNUMBER.getName()), queryRoot.get(EntityConstant.CITY.getName()),
					queryRoot.get(EntityConstant.STATE.getName()), queryRoot.get(EntityConstant.PINCODE.getName()),
					queryRoot.get(EntityConstant.ISDETAILCOMPLETED.getName()),
					queryRoot.get(EntityConstant.USERTYPE.getName()),
					queryRoot.get(EntityConstant.ISPHONEVERIFIED.getName()),

					queryRoot.get(EntityConstant.ISACCREDITEDCUSTOMER.getName()),
					queryRoot.get(EntityConstant.ISPREMIUMPRINTCUSTOMER.getName()),
					queryRoot.get(EntityConstant.ISPREMIUMRADIOCUSTOMER.getName()),
					queryRoot.get(EntityConstant.ISPREMIUMDIGITALCUSTOMER.getName()),
					queryRoot.get(EntityConstant.ISPDCENABLED.getName()),
					queryRoot.get(EntityConstant.OWNERID.getName())));

			List<Predicate> predicates = getPredicateByIdandStatus(id, queryBuilder, queryRoot);

			query.where(queryBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
			TypedQuery<UserDetailDTO> typedQuery = entityManager.createQuery(query);
			return Optional.of(typedQuery.getSingleResult());
		} catch (Exception e) {
			logger.catching(e);
			return Optional.empty();
		}
	}

	public List<Predicate> getPredicateByIdandStatus(Long id, CriteriaBuilder queryBuilder, Root<User> queryRoot) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(queryBuilder.equal(queryRoot.get(EntityConstant.ID.getName()), id));
		predicates.add(queryBuilder.notEqual(queryRoot.get(EntityConstant.STATUS.getName()), Status.DELETED));
		return predicates;
	}

	@Override
	public List<UserListResponseDTO> getUsers(UserSearchDTO userSearchDTO) {
		logger.debug("getUsers Method Called in UserDao.");

		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserListResponseDTO> query = queryBuilder.createQuery(UserListResponseDTO.class);
		Root<User> userRoot = query.from(User.class);
		Join<User, Role> roleJoin = userRoot.join(EntityConstant.ROLE.getName());

		// get All Predicates condition List
		List<Predicate> predicates = getPredicates(userSearchDTO, queryBuilder, userRoot, roleJoin);

		if (userSearchDTO.getSmartSearch() != null) {
			setSearchLikeFilter(userSearchDTO, queryBuilder, userRoot, predicates);
		}

		query.select(queryBuilder.construct(UserListResponseDTO.class, userRoot.get(EntityConstant.ID.getName()),
				userRoot.get(EntityConstant.FNAME.getName()), userRoot.get(EntityConstant.LNAME.getName()),
				userRoot.get(EntityConstant.OWNERID.getName()), userRoot.get(EntityConstant.EMAIL.getName()),
				userRoot.get(EntityConstant.ROLE.getName()), userRoot.get(EntityConstant.CITY.getName()),
				userRoot.get(EntityConstant.PHONE1.getName()), userRoot.get(EntityConstant.USERIMAGE.getName()),
				userRoot.get(EntityConstant.USERTYPE.getName()), userRoot.get(EntityConstant.CREATEDAT.getName()),
				userRoot.get(EntityConstant.COMPNAME.getName()),
				userRoot.get(EntityConstant.ISPREMIUMPRINTCUSTOMER.getName()),
				userRoot.get(EntityConstant.ISACCREDITEDCUSTOMER.getName()),
				userRoot.get(EntityConstant.ISPDCENABLED.getName()),
				userRoot.get(EntityConstant.ISPREMIUMRADIOCUSTOMER.getName())));

		if (null != userSearchDTO.getSortBy()) {
			if (null == userSearchDTO.getSortDir()) {
				userSearchDTO.setSortDir(EntityConstant.ASC.getName());
			}
			// set Order by Conditions on many columns
			setOrderConditions(userSearchDTO.getSortBy(), queryBuilder, query, userRoot, userSearchDTO.getSortDir());
		} else {
			query.orderBy(queryBuilder.desc(userRoot.get(EntityConstant.CREATEDAT.getName())));
		}

		query.where(queryBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

		TypedQuery<UserListResponseDTO> typedQuery = entityManager.createQuery(query);

		if (userSearchDTO.getPage() != null && userSearchDTO.getSize() != null && userSearchDTO.getPage() != 0
				&& userSearchDTO.getSize() != 0) {
			typedQuery.setFirstResult((userSearchDTO.getPage().intValue() - 1) * userSearchDTO.getSize().intValue());
			typedQuery.setMaxResults(userSearchDTO.getSize().intValue());
		}

		return typedQuery.getResultList();
	}

	private void setOrderConditions(String sortBy, CriteriaBuilder queryBuilder,
			CriteriaQuery<UserListResponseDTO> query, Root<User> userRoot, String sortDir) {

		logger.debug("setOrderConditions Method Called in CompanyDao.");
		if (sortDir.compareToIgnoreCase(EntityConstant.ASC.getName()) == 0) {
			if (sortBy.equalsIgnoreCase(EntityConstant.FNAME.getName()))
				query.orderBy(queryBuilder.asc(userRoot.get(EntityConstant.FNAME.getName())));
			else if (sortBy.equalsIgnoreCase(EntityConstant.EMAIL.getName()))
				query.orderBy(queryBuilder.asc(userRoot.get(EntityConstant.EMAIL.getName())));
			else if (sortBy.equalsIgnoreCase(EntityConstant.CITY.getName()))
				query.orderBy(queryBuilder.asc(userRoot.get(EntityConstant.CITY.getName())));
			else if (sortBy.equalsIgnoreCase(EntityConstant.USERTYPE.getName()))
				query.orderBy(queryBuilder.asc(userRoot.get(EntityConstant.USERTYPE.getName())));
			else if (sortBy.equalsIgnoreCase(EntityConstant.CREATEDAT.getName()))
				query.orderBy(queryBuilder.asc(userRoot.get(EntityConstant.CREATEDAT.getName())));
			else
				query.orderBy(queryBuilder.asc(userRoot.get(sortBy)));
		} else {
			if (sortBy.equalsIgnoreCase(EntityConstant.FNAME.getName()))
				query.orderBy(queryBuilder.desc(userRoot.get(EntityConstant.FNAME.getName())));
			else if (sortBy.equalsIgnoreCase(EntityConstant.EMAIL.getName()))
				query.orderBy(queryBuilder.desc(userRoot.get(EntityConstant.EMAIL.getName())));
			else if (sortBy.equalsIgnoreCase(EntityConstant.CITY.getName()))
				query.orderBy(queryBuilder.desc(userRoot.get(EntityConstant.CITY.getName())));
			else if (sortBy.equalsIgnoreCase(EntityConstant.USERTYPE.getName()))
				query.orderBy(queryBuilder.desc(userRoot.get(EntityConstant.USERTYPE.getName())));
			else if (sortBy.equalsIgnoreCase(EntityConstant.CREATEDAT.getName()))
				query.orderBy(queryBuilder.desc(userRoot.get(EntityConstant.CREATEDAT.getName())));
			else
				query.orderBy(queryBuilder.desc(userRoot.get(sortBy)));
		}

	}

	private void setSearchLikeFilter(UserSearchDTO userSearchDTO, CriteriaBuilder queryBuilder, Root<User> userRoot,
			List<Predicate> predicates) {

		logger.debug("setSearchLikeFilter Method Called in UserDao.");

		String toBeSearched = null;
		List<Predicate> smartSearchPredicateList = new ArrayList<>();

		toBeSearched = QueryUtil.convertForLike(userSearchDTO.getSmartSearch());

		Expression<String> expressionForFullName = queryBuilder.concat(
				queryBuilder.concat(userRoot.get(EntityConstant.FNAME.getName()), " "),
				userRoot.get(EntityConstant.LNAME.getName()));

		// user name
		smartSearchPredicateList.add(queryBuilder.like(expressionForFullName, toBeSearched));

		// user company
		smartSearchPredicateList.add(queryBuilder.like(userRoot.get(EntityConstant.COMPNAME.getName()), toBeSearched));

		// user phone
		smartSearchPredicateList.add(queryBuilder.like(userRoot.get(EntityConstant.PHONE1.getName()), toBeSearched));

		// email
		smartSearchPredicateList.add(queryBuilder.like(userRoot.get(EntityConstant.EMAIL.getName()), toBeSearched));

		Predicate[] p = new Predicate[smartSearchPredicateList.size()];
		smartSearchPredicateList.toArray(p);
		predicates.add(queryBuilder.or(p));

	}

	private List<Predicate> getPredicates(UserSearchDTO userSearchDTO, CriteriaBuilder queryBuilder,
			Root<User> queryRoot, Join<User, Role> roleJoin) {
		logger.debug("getPredicates Method Called in EventsDaoImpl.");
		List<Predicate> predicates = new ArrayList<>();

		predicates.add(queryBuilder.notEqual(queryRoot.get(EntityConstant.STATUS.getName()), Status.DELETED));
		// predicates.add(queryBuilder.notEqual(queryRoot.get(EntityConstant.USERTYPE.getName()),
		// UserType.C));
		// predicates.add(queryBuilder.equal(roleJoin.get(EntityConstant.ROLEIDENTIFIER.getName()),
		// "User"));
		predicates.add(queryBuilder.in(roleJoin.get(EntityConstant.ROLEIDENTIFIER.getName())).value("User").value("L1")
				.value("L2").value("L3"));
		// status
		if (userSearchDTO.getStatus() != null && userSearchDTO.getStatus().length() > 0) {
			List<Status> statusList = Arrays.asList(userSearchDTO.getStatus().split(",")).stream()
					.map(Status::fromShortName).collect(Collectors.toList());
			Predicate parentStatusPredicate = queryRoot.get(EntityConstant.STATUS.getName()).in(statusList);
			predicates.add(parentStatusPredicate);
		}
		// City
		//
		if (userSearchDTO.getCity() != null) {

			predicates.add(queryBuilder.equal(queryRoot.get(EntityConstant.CITY.getName()), userSearchDTO.getCity()));

		}
		// User Type
		if (userSearchDTO.getUserType() != null) {

			predicates.add(
					queryBuilder.equal(queryRoot.get(EntityConstant.USERTYPE.getName()), userSearchDTO.getUserType()));

		}
		// ACC User
		if (userSearchDTO.getIsAccreditedCustomer() != null) {
			predicates.add(queryBuilder.equal(queryRoot.get(EntityConstant.ISACCREDITEDCUSTOMER.getName()),
					userSearchDTO.getIsAccreditedCustomer()));
		}

		// Premium User
		if (userSearchDTO.getIsPremiumPrintCustomer() != null) {
			predicates.add(queryBuilder.equal(queryRoot.get(EntityConstant.ISPREMIUMPRINTCUSTOMER.getName()),
					userSearchDTO.getIsPremiumPrintCustomer()));
		}
		if (userSearchDTO.getIsPremiumRadioCustomer() != null) {
			predicates.add(queryBuilder.equal(queryRoot.get(EntityConstant.ISPREMIUMRADIOCUSTOMER.getName()),
					userSearchDTO.getIsPremiumRadioCustomer()));
		}
		if (userSearchDTO.getIsPremiumDigitalCustomer() != null) {
			predicates.add(queryBuilder.equal(queryRoot.get(EntityConstant.ISPREMIUMDIGITALCUSTOMER.getName()),
					userSearchDTO.getIsPremiumDigitalCustomer()));
		}
		return predicates;
	}

	@Override
	public Long count(UserSearchDTO userSearchDTO) {
		logger.debug("count Method Called in EventsDao.");

		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = queryBuilder.createQuery(Long.class);
		Root<User> userRoot = query.from(User.class);
		Join<User, Role> roleJoin = userRoot.join(EntityConstant.ROLE.getName());
		query.select(queryBuilder.count(userRoot));

		// get All Predicates condition List
		List<Predicate> predicates = getPredicates(userSearchDTO, queryBuilder, userRoot, roleJoin);

		if (userSearchDTO.getSmartSearch() != null) {
			setSearchLikeFilter(userSearchDTO, queryBuilder, userRoot, predicates);
		}

		query.where(queryBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

		return entityManager.createQuery(query).getSingleResult();
	}

	@Override
	public Long countDistinctRegisteredUsers(UserSearchDTO userSearchDTO) {
		logger.debug("countDistinctRegisteredUsers Method Called in UserDao.");

		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = queryBuilder.createQuery(Long.class);
		Root<User> userRoot = query.from(User.class);
		Join<User, Role> roleJoin = userRoot.join(EntityConstant.ROLE.getName());
		query.select(queryBuilder.countDistinct(userRoot));

		// get All Predicates condition List
		List<Predicate> predicates = getPredicates(userSearchDTO, queryBuilder, userRoot, roleJoin);

		query.where(queryBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

		return entityManager.createQuery(query).getSingleResult();
	}

	@Override
	public Optional<UserDetailsDTO> getBasicUserDetailDTO(Long id) {
		logger.debug("getBasicUserDetailDTO Method Called in UserDao.");
		try {
			CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsDTO> query = queryBuilder.createQuery(UserDetailsDTO.class);
			Root<User> queryRoot = query.from(User.class);
			List<Predicate> predicates = new ArrayList<>();
			query.select(queryBuilder.construct(UserDetailsDTO.class, queryRoot.get(EntityConstant.FNAME.getName()),
					queryRoot.get(EntityConstant.LNAME.getName()), queryRoot.get(EntityConstant.EMAIL.getName()),
					queryRoot.get(EntityConstant.PHONE1.getName()), queryRoot.get(EntityConstant.COMPNAME.getName()),
					queryRoot.get(EntityConstant.ADDRESS.getName()), queryRoot.get(EntityConstant.GSTNUMBER.getName()),
					queryRoot.get(EntityConstant.PANNUMBER.getName()), queryRoot.get(EntityConstant.CITY.getName()),
					queryRoot.get(EntityConstant.STATE.getName()), queryRoot.get(EntityConstant.PINCODE.getName()),
					queryRoot.get(EntityConstant.USERTYPE.getName()),
					queryRoot.get(EntityConstant.ISDETAILCOMPLETED.getName())));
			predicates.add(queryBuilder.equal(queryRoot.get(EntityConstant.ID.getName()), id));
			query.where(queryBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
			TypedQuery<UserDetailsDTO> typedQuery = entityManager.createQuery(query);
			return Optional.of(typedQuery.getSingleResult());
		} catch (Exception e) {
			logger.catching(e);
			return Optional.empty();
		}
	}

	@Override
	public List<CityDTOResponse> getUserCity() {
		logger.debug("UserDao getUserCity Method Called");

		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CityDTOResponse> query = queryBuilder.createQuery(CityDTOResponse.class);
		Root<User> userRoot = query.from(User.class);
		query.select(queryBuilder.construct(CityDTOResponse.class, userRoot.get(EntityConstant.CITYID.getName()),
				userRoot.get(EntityConstant.CITY.getName()))).distinct(true);
		query.orderBy(queryBuilder.asc(userRoot.get(EntityConstant.CITY.getName())));
		query.where(userRoot.get(EntityConstant.CITY.getName()).isNotNull());

		TypedQuery<CityDTOResponse> typedQuery = entityManager.createQuery(query);
		return typedQuery.getResultList();
	}

	@Override
	public Long getUserCityCount() {
		logger.debug("getUserCityCount UserDao Method Called.");
		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = queryBuilder.createQuery(Long.class);
		Root<User> userRoot = query.from(User.class);
		query.select(queryBuilder.count(userRoot.get(EntityConstant.CITY.getName()))).distinct(true);
		query.where(userRoot.get(EntityConstant.CITY.getName()).isNotNull());
		return entityManager.createQuery(query).getSingleResult();
	}

//	@Override
//	public List<User> getAllTeam(AdminUserSearch userRequestDTO) {
//		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//		CriteriaQuery<User> query = cb.createQuery(User.class);
//		Root<User> user = query.from(User.class);
//		List<Predicate> predicates = new ArrayList<>();
//		if (userRequestDTO.getReferralCode() != null && !userRequestDTO.getReferralCode().isEmpty()) {
//			predicates.add(cb.equal(user.get("referralCode"), userRequestDTO.getReferralCode()));
//		}
//		if (userRequestDTO.getStartDate() != null && userRequestDTO.getEndDate() != null) {
//			predicates
//					.add(cb.between(user.get("createdAt"), convertDate(userRequestDTO.getStartDate()), convertDate(userRequestDTO.getEndDate())));
//		}
//		if (userRequestDTO.getSpecificDate() != null) {
//			predicates.add(cb.equal(user.get("createdAt"), convertDate(userRequestDTO.getSpecificDate())));
//		}
//		query.select(user).where(predicates.toArray(new Predicate[0]));
//		return entityManager.createQuery(query).getResultList();
//	}

	@Override
	public List<User> getAllTeam(AdminUserSearch userRequestDTO) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = cb.createQuery(User.class);
		Root<User> user = query.from(User.class);
		List<Predicate> predicates = new ArrayList<>();

		if (userRequestDTO.getStatus() != null) {

			if (userRequestDTO.getStatus() == "0") {
				// predicates.add(cb.equal(user.get("status"), userRequestDTO.getStatus()));
				predicates.add(cb.equal(user.get("status"), Status.ARCHIVED));
			}
			if (userRequestDTO.getStatus() == "1") {
				// predicates.add(cb.equal(user.get("status"), userRequestDTO.getStatus()));
				predicates.add(cb.equal(user.get("status"), Status.ACTIVE));
			}
		}

		if (userRequestDTO.getReferralCode() != null && !userRequestDTO.getReferralCode().isEmpty()) {
			predicates.add(cb.equal(user.get("referralCode"), userRequestDTO.getReferralCode()));
		}

		if (userRequestDTO.getMobileNumber() != null && !userRequestDTO.getMobileNumber().isEmpty()) {
			predicates.add(cb.equal(user.get("mobileNumber"), userRequestDTO.getMobileNumber()));
		}

		if (userRequestDTO.getStartDate() != null && userRequestDTO.getEndDate() != null) {
			predicates.add(cb.between(cb.function("DATE", Date.class, user.get("createdAt")),
					convertDate(userRequestDTO.getStartDate()), convertDate(userRequestDTO.getEndDate())));
		}

		if (userRequestDTO.getSpecificDate() != null) {
			predicates.add(cb.equal(cb.function("DATE", Date.class, user.get("createdAt")),
					convertDate(userRequestDTO.getSpecificDate())));
		}

		query.select(user).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(query).getResultList();
	}

	private Date convertDate(String payload) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(payload);
		} catch (ParseException e) {
			System.out.println(e);
		}
		return date;
	}

}
