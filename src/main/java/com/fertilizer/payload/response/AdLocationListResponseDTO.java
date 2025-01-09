package com.fertilizer.payload.response;

import com.fertilizer.enums.Channel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdLocationListResponseDTO {
	
	private Long locationId;

	private String adLocationName;

	private String adLocationCode;
	
	private Channel channel;

	public AdLocationListResponseDTO(Long locationId, String adLocationName, String adLocationCode, Channel channel) {
		this.locationId = locationId;
		this.adLocationName = adLocationName;
		this.adLocationCode = adLocationCode;
		this.channel = channel;
	}
}
