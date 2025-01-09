package com.fertilizer.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fertilizer.enums.PaymentModeEnum;
import com.fertilizer.enums.PaymentStatus;
import com.fertilizer.enums.PurchaseFor;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_payment")
@DynamicUpdate
@Getter
@Setter
public class OrderPayment extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long orderId;
	private String orderPackagesId;
	private Long userId;
	private Long clientId;
	private Double paymentAmount;
	private String invoiceId;
	private String invoicePdfName;
	private String tinyUrl;
	private PaymentStatus paymentStatus;
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
	private String encRequest;
	private String encResponse;
	private String promoCode;
	private Long promoId;
	private String voucherIds;
	private String voucherCodes;
	private Double voucherDiscount;
	private Double voucherDiscountPercent;
	private Double gstValue;
	private Double agencyDiscount;
	private Double agencyDiscountPercent;
	private Double promoDiscount;
	private Double slabDiscount;
	private Double slabDiscountPercent;
	private PaymentModeEnum paymentMode;
	private Double voucherGst;
	private Double voucherGstPercent;
}
