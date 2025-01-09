package com.fertilizer.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fertilizer.enums.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "orders")
@DynamicUpdate
@Getter
@Setter
public class Orders extends BaseModel {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	private Long orderPaymentId;

	private Channel orderType;

	private PurchaseFor orderFor;

//	@JsonBackReference
//	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST,
//			CascadeType.REFRESH }, optional = false)
//	@JoinColumn(name = "client_id")
//	private Clients client;

	private Double orderAmount;

	private String promoCode;

	private Long promoId;

	private Double totalPromoDiscount;

	private String voucherIds;

	private String voucherCodes;

	private Double totalVoucherDiscount;

	private Double gstPercent;

	private Double totalGstValue;

	private Double totalAgencyDiscount;

	private Double totalSlabDiscount;

	private Double slabDiscountPercent;

	private Double voucherDiscountPercent;

	private Double agencyDiscountPercent;

	private String invPdfName;

	private String address;

	private String city;

	private String state;

	private Long stateId;

	private Long cityId;

	private String pincode;

	private String gstNumber;

	private String panNumber;

	private String encRequest;

	private String encResponse;

	private PaymentStatus paymentStatus;

	private String cancelReason;

	private String rejectionReason;

	private PaymentRefundStatus paymentRefundStatus;

	private Long paymentRefundId;

	private PaymentModeEnum paymentMode;

	private Double voucherGst;

	private Double voucherGstPercent;

	private BooleanEnum isPremiumOrder = BooleanEnum.NO;

	private String internalOrderNumber;

	private String roFileName;
	
	private String roLogs;
	
	private String pdcFileName;
	
	private String pdcLogs;

	private String bookerEmail;

	private String bookerPhone;

	private String salesEmail;

	private ComposeStatus orderComposeStatus;

	private Long orderComposedBy;

	private Long orderApprovedBy;

	private Date orderApprovedAt;

	private Date orderRejectedAt;

	private Long orderRejectedBy;

	private Long sfdcRequestId;

	private OpportunityRequestStatus sfdcRequestStatus;

	private String sfdcOpportunityId;

	private Long ownerId;

	private Double refundAmount;

	private Double cancellationCharge;

	//
	private CancellationStatus cancellationStatus;

}
