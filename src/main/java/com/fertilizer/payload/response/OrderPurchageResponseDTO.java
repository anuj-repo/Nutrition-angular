package com.fertilizer.payload.response;

import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.PaymentModeEnum;
import com.fertilizer.enums.PaymentStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class OrderPurchageResponseDTO {

	private String email;
	
	private String paymentLink;
	
	private String action;
	
	private String encRequest;
	
	private String accessCode;

	private Long adOrderId;
	
	private String adOrderPackageId;
	
	private Long paymentId;
	
	private BooleanEnum isPremiumOrder;
	
	private UserProfileDetailDTO userProfileDetailDTO;
	
	private PaymentModeEnum paymentMode;
	
	private PaymentStatus paymentStatus;
	
	private Boolean isGenerateQuickInvoice;
	
	public OrderPurchageResponseDTO(String email, String paymentLink) {
		this.email = email;
		this.paymentLink = paymentLink;
	}
	
}
