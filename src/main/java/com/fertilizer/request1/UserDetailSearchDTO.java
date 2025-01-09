package com.fertilizer.request1;
 
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailSearchDTO extends CommonSearchDto {

	private Boolean voucherListFlag;
	private Long userId;
	private String purchaseFor;
	private Long clientId;
	
	public UserDetailSearchDTO(UserDetaiListlDTO userDetailDTO) {
		super(userDetailDTO.getSmartSearch(), userDetailDTO.getPage(), userDetailDTO.getSize(),
				userDetailDTO.getSortBy(), userDetailDTO.getSortDir());

		this.voucherListFlag = userDetailDTO.getVoucherListFlag();
		this.userId = userDetailDTO.getUserId();
		this.purchaseFor = userDetailDTO.getPurchaseFor();
		this.clientId=userDetailDTO.getClientId();

	}
}
