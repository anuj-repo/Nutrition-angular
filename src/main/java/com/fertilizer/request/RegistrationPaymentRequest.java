package com.fertilizer.request;

import javax.persistence.Column;

import com.fertilizer.enums.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationPaymentRequest {

	private String utrNumber;

	@Column(length = 50000000)
	private byte[] picByte;

	private PaymentStatus paymentStatus;

}
