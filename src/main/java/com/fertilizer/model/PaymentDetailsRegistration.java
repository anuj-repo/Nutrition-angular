package com.fertilizer.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fertilizer.enums.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@DynamicUpdate
@Table(name = "payment_details_registration")
@Getter
@Setter
public class PaymentDetailsRegistration extends ParentBaseModel {

	private static final long serialVersionUID = -4864520840627628591L;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private String utrNumber;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "registration_payment_images", joinColumns = {
			@JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "image_id") })
	private Set<RegistrationPaymentImage> paymentImages;

	private PaymentStatus paymentStatus;

	// private String transactionId;

}
