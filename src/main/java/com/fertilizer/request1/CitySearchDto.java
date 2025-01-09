package com.fertilizer.request1;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CitySearchDto extends CommonSearchDto {

	private Long id;

	private Long countryId;

	private Long stateId;

	private String cityName;

	private String cityType;

	private String status;
	
	private String publicUse;

	public CitySearchDto() {

	}

	public CitySearchDto(Long id, Long countryId, Long stateId, String cityName, String cityType, String status,
			String smartSearch,String publicUse, Long page, Long size, String sortBy, String sortDir) {
		super(smartSearch, page, size, sortBy, sortDir);
		this.id = id;
		this.countryId = countryId;
		this.stateId = stateId;
		this.cityName = cityName;
		this.cityType = cityType;
		this.status = status;
		this.publicUse =publicUse;
	}

	public CitySearchDto(@Valid CityListRequestDTO cityListRequestDto) {

		super(cityListRequestDto.getSmartSearch(), cityListRequestDto.getPage(), cityListRequestDto.getSize(), cityListRequestDto.getSortBy(),
				cityListRequestDto.getSortDir());
		this.id = cityListRequestDto.getId();
		this.countryId = cityListRequestDto.getCountryId();
		this.stateId = cityListRequestDto.getStateId();
		this.cityName = cityListRequestDto.getCityName();
		this.cityType = cityListRequestDto.getCityType();
		this.status = cityListRequestDto.getStatus();
		this.publicUse =cityListRequestDto.getPublicUse();
	}
}
