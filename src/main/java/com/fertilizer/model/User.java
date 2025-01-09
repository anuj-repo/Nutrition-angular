package com.fertilizer.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.GenderEnum;
import com.fertilizer.enums.PackageChoiceEnum;
import com.fertilizer.enums.PackageTakenEnum;
import com.fertilizer.enums.Salutation;
import com.fertilizer.enums.UserLevel;
import com.fertilizer.enums.UserType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
@DynamicUpdate
@Getter
@Setter
public class User extends BaseModel {

	private static final long serialVersionUID = -4864520840627628591L;

	// @ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	@JsonIgnore
	private User parent;

	// @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade =
	// CascadeType.ALL)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
	private List<User> children = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY)
	private List<PaymentDetailsRegistration> registrationPaymentDetails;

	@OneToMany(fetch = FetchType.LAZY)
	private List<PaymentDetails> paymentDetails;

	private Salutation salutation;

	private String fname;

	private String lname;

	@Email
	private String email;

	private String username;

	private UserType userType;

	private String referralCode;

	private String referralCodeUsed;

	private String mobileNumber;

	@JsonIgnore
	private String password;

	@JsonIgnore
	private String userPassword;

	private GenderEnum gender;

	private LocalDate dob;

	@JsonIgnore
	private String rawPassword;

	@JsonIgnore
	private String forgetPasswordToken;

	@JsonIgnore
	private Date userChangedPasswordAt;

	private String address;

	private String state;

	private String country;

	private String pincode;

	private String city;

	private String userImage;

//	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	@JoinTable(name = "agency_clients", joinColumns = { @JoinColumn(name = "agency_id") }, inverseJoinColumns = {
//			@JoinColumn(name = "client_id") })
//	private Set<Clients> client = new HashSet<Clients>();
//
//	private Long clientId;
//
//	private String agencyId;

	private BooleanEnum isDetailCompleted;

	private BooleanEnum isPhoneVerified;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Role role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<UserAddress> userAddress;

	private String passwordResetLink;

	private Date passwordLinkGeneratedAt;

	private String passwordChangeLog;

	// private String upiId;

	private PackageTakenEnum packageTaken;

	private PackageChoiceEnum productChoice;

	private BigDecimal totalAccountBalance;

	private BigDecimal paidBalance;

	private BigDecimal accountBalance;

	private UserLevel userLevel;

//	private Long ownerId;
}