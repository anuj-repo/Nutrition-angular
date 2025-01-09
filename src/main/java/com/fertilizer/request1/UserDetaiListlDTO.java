package com.fertilizer.request1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetaiListlDTO extends CommonRequestDTO  {
	
	private Boolean voucherListFlag;
	private Long userId;
	private String purchaseFor;
	private Long clientId;
	
	public UserDetaiListlDTO(String smartSearch, String sortBy, String sortDir, Long page, Long size,Boolean voucherListFlag,Long userId,Long clientId,String purchaseFor) {
		super(smartSearch, sortBy, sortDir, page, size);
		this.voucherListFlag = voucherListFlag;
		this.userId = userId;
		this.clientId=clientId;
		this.purchaseFor = purchaseFor;
	}
}
