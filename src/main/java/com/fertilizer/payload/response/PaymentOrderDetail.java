package com.fertilizer.payload.response;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fertilizer.enums.Channel;
import com.fertilizer.enums.PaymentStatus;
import com.fertilizer.enums.PurchaseFor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentOrderDetail {
	
	private Long orderId;
	private String orderPackagesId;
	private Long userId;
	private Long clientId;
	private Double paymentAmount;
	private String invoiceId;
	private String tinyUrl;
	private PaymentStatus paymentStatus;
	@JsonSerialize(using = ToStringSerializer.class)
	private Date paymentAt;
	private String errorCode;
	private String errorDescription;
	private String merchantReferenceNo;
	private String address;
	private String city;
	private Long cityId;
	private Long stateId;
	private String state;
	private String pincode;
	private String gstNumber;
	private String panNumber;
	private PurchaseFor purchaseFor;
	private String clientCompName;
	private Channel channel;
	private String emailId;
	private String phone;
	public PaymentOrderDetail(Long orderId, String orderPackagesId, Long userId, Long clientId, Double paymentAmount,
			String invoiceId, String tinyUrl, PaymentStatus paymentStatus, Date paymentAt, String errorCode,
			String errorDescription, String merchantReferenceNo, String address, String city, Long cityId, Long stateId,
			String state, String pincode, String gstNumber, String panNumber, PurchaseFor purchaseFor,
			String clientCompName) {
		this.orderId = orderId;
		this.orderPackagesId = orderPackagesId;
		this.userId = userId;
		this.clientId = clientId;
		this.paymentAmount = paymentAmount;
		this.invoiceId = invoiceId;
		this.tinyUrl = tinyUrl;
		this.paymentStatus = paymentStatus;
		this.paymentAt = paymentAt;
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
		this.merchantReferenceNo = merchantReferenceNo;
		this.address = address;
		this.city = city;
		this.cityId = cityId;
		this.stateId = stateId;
		this.state = state;
		this.pincode = pincode;
		this.gstNumber = gstNumber;
		this.panNumber = panNumber;
		this.purchaseFor = purchaseFor;
		this.clientCompName = clientCompName;
	}
	
	
}
