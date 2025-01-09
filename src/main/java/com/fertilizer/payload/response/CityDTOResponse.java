package com.fertilizer.payload.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDTOResponse {
	private Long cityId;
	private String cityName;
	
	public CityDTOResponse(Long cityId, String cityName) {
		super();
		this.cityId = cityId;
		this.cityName = cityName;
	}
}
