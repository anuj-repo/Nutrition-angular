package com.fertilizer.payload.response;

import lombok.Data;

@Data
public class CardDetailsResponse {

	public Integer id;
	public String productName;
	public String productDescription;
	public Double productActualPrice;
	public Double productDiscountedPrice;

	public CardDetailsResponse(Integer id, String productName, String productDescription, Double productActualPrice,
			Double productDiscountedPrice) {
		super();
		this.id = id;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productActualPrice = productActualPrice;
		this.productDiscountedPrice = productDiscountedPrice;
	}

}
