package com.fertilizer.payload.response;

import lombok.Data;

@Data
public class OrderDetailsResponse {

	public Integer id;
	public String productName;
	public String name;
	public String address;
	public String contactNo;
	public String status;
	public Double orderAmount;
	
	
	public OrderDetailsResponse(Integer id, String productName, String name, String address, String contactNo,
			String status,Double orderAmount) {
		super();
		this.id = id;
		this.productName = productName;
		this.name = name;
		this.address = address;
		this.contactNo = contactNo;
		this.status = status;
		this.orderAmount=orderAmount;
	}
	
	
	
	
	
	
}
