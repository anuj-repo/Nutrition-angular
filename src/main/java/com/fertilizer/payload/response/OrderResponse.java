package com.fertilizer.payload.response;

import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.PaymentModeEnum;
import com.fertilizer.enums.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

	private String message;

	private String paymentUrl;

	private String mobileNo;

	private String email;

	private PaymentModeEnum paymentMode;

	private Long orderId;

	private BooleanEnum isPremiumOrder;

	private BooleanEnum isAccUser;

	private PaymentStatus paymentStatus;
	
	private Long paymentId;

	public OrderResponse(String message, String paymentUrl, String mobileNo, String email, PaymentModeEnum paymentMode,
			Long orderId, BooleanEnum isPremiumOrder, BooleanEnum isAccUser, PaymentStatus paymentStatus,Long paymentId) {
		this.message = message;
		this.paymentUrl = paymentUrl;
		this.mobileNo = mobileNo;
		this.email = email;
		this.paymentMode = paymentMode;
		this.orderId = orderId;
		this.isPremiumOrder = isPremiumOrder;
		this.isAccUser = isAccUser;
		this.paymentStatus = paymentStatus;
		this.paymentId = paymentId;
	}

}
