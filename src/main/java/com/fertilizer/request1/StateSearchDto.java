package com.fertilizer.request1;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateSearchDto extends CommonSearchDto {
	private Long countryId;

	private String stateName;

	private String stateCode;

	private String status;

	public StateSearchDto() {

	}

	public StateSearchDto(Long countryId, String stateName, String stateCode, String smartSearch, String status,
			Long page, Long size, String sortBy, String sortDir) {
		super(smartSearch, page, size, sortBy, sortDir);
		this.countryId = countryId;
		this.stateName = stateName;
		this.stateCode = stateCode;
		this.status = status;
	}

	public StateSearchDto(@Valid StateListRequestDTO stateListRequestDTO) {

		super(stateListRequestDTO.getSmartSearch(), stateListRequestDTO.getPage(), stateListRequestDTO.getSize(),
				stateListRequestDTO.getSortBy(), stateListRequestDTO.getSortDir());
		this.countryId = stateListRequestDTO.getCountryId();
		this.stateName = stateListRequestDTO.getStateName();
		this.stateCode = stateListRequestDTO.getStateCode();
		this.status = stateListRequestDTO.getStatus();
	}
}
