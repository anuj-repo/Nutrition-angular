package com.fertilizer.request;

import lombok.Data;

@Data
public class UpdatePaymentPayload {

	private Long userId;

	private String amount;

}
