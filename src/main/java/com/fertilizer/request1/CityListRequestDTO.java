package com.fertilizer.request1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityListRequestDTO extends CommonRequestDTO {

	private Long id;
	private Long countryId;
	private Long stateId;
	private String cityName;
	private String cityType;
	private String status;
	private String publicUse;
	
	public CityListRequestDTO(String smartSearch, String sortBy, String sortDir, Long page, Long size, Long id,
			Long countryId, Long stateId, String cityName, String cityType, String status,String publicUse) {
		super(smartSearch, sortBy, sortDir, page, size);
		this.id = id;
		this.countryId = countryId;
		this.stateId = stateId;
		this.cityName = cityName;
		this.cityType = cityType;
		this.status = status;
		this.publicUse=publicUse;
	}


}
