package com.fertilizer.request1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateListRequestDTO extends CommonRequestDTO {
	
	private Long countryId;

	private String stateName;

	private String stateCode;

	private String status;

	public StateListRequestDTO(String smartSearch, String sortBy, String sortDir, Long page, Long size, Long countryId,
			String stateName, String stateCode, String status) {
		super(smartSearch, sortBy, sortDir, page, size);
		this.countryId = countryId;
		this.stateName = stateName;
		this.stateCode = stateCode;
		this.status = status;
	}

}
