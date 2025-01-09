package com.fertilizer.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubOrderInvoiceDTO {

	private String packageId;
	private String channel;
	private String amount;

	public SubOrderInvoiceDTO(String packageId, String channel, String amount) {
		super();
		this.packageId = packageId;
		this.channel = channel;
		this.amount = amount;
	}

}
