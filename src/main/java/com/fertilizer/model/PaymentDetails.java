package com.fertilizer.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Entity
@DynamicUpdate
@Table(name = "payment_details")
@Getter
@Setter
public class PaymentDetails extends BaseModel {

	private static final long serialVersionUID = -4864520840627628591L;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private String utrNumber;

	private String amountToPay;

	private String totalAmountPaid;

	private String paymentStatus;

}
