package com.fertilizer.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderMailDTO {

	private String packageId;

	private String orderId;
	private String channelOfAdvertisement;
	private String creationDate;
	private String publishingDates;
	private String rejectionReason;
	private String orderDetailPageLink;
	private String orderInvoiceUrl;
	private String isDigitalFlow;
	
	
	public OrderMailDTO(String packageId, String channelOfAdvertisement, String creationDate, String publishingDates,
			String rejectionReason, String orderDetailPageLink, String orderId, String isDigitalFlow) {
		super();
		this.packageId = packageId;
		this.channelOfAdvertisement = channelOfAdvertisement;
		this.creationDate = creationDate;
		this.publishingDates = publishingDates;
		this.rejectionReason = rejectionReason;
		this.orderDetailPageLink = orderDetailPageLink;
		this.orderId = orderId;
		this.isDigitalFlow = isDigitalFlow;
	}
	
	public OrderMailDTO(String packageId, String channelOfAdvertisement, String creationDate, String publishingDates,
			String orderDetailPageLink, String orderId) {
		super();
		this.packageId = packageId;
		this.channelOfAdvertisement = channelOfAdvertisement;
		this.creationDate = creationDate;
		this.publishingDates = publishingDates;
		this.orderDetailPageLink = orderDetailPageLink;
		this.orderId = orderId;
	}

	public OrderMailDTO(String packageId, String creationDate, String orderId) {
		super();
		this.packageId = packageId;
		this.creationDate = creationDate;
		this.orderId = orderId;
	}
	
	
	
	
	

}


