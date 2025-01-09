package com.fertilizer.request;

import com.fertilizer.request1.CommonRequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryListRequestDTO extends CommonRequestDTO {
	private Long countryId;

	private String countryName;

	private String countryCode;

	private String status;

	public CountryListRequestDTO(String smartSearch, String sortBy, String sortDir, Long page, Long size,
			Long countryId, String countryName, String countryCode, String status) {
		super(smartSearch, sortBy, sortDir, page, size);
		this.countryId = countryId;
		this.countryName = countryName;
		this.countryCode = countryCode;
		this.status = status;
	}

}
