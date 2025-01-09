package com.fertilizer.request1;

import javax.validation.constraints.NotNull;

import com.fertilizer.enums.Channel;
import com.fertilizer.enums.FileUploadEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateDTO {

	private Long userId;

	private Long clientId;

	private Double amountToPay;

	

	private Channel channel;

	private Long campaignId;

	@NotNull
	private String purchaseFor;

	private String clientCompanyName;

}
