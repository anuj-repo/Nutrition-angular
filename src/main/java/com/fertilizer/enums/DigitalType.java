package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DigitalType {
	CAROUSEL("carousel"), IGTVMOBILE("igtv_mobile"),IGTVPRODUCED("igtv_produced"),LIVE("live"),LIVEOB("live_ob"),
	LIVESTUDIO("live_studio"),STATIC("static"),STORY("story"),TWEET("tweet"),VIDEOMOBILE("video_mobile"),
	VIDEOPRODUCED("video_produced");

	private final String name;

	private DigitalType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	@JsonCreator
	public static DigitalType forWholeDayName(String name) {

		for (DigitalType state : DigitalType.values()) {
			if (state.getName().equalsIgnoreCase(name)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Day name " + name);
	}
	
	public static DigitalType fromShortName(String shortName) {
		switch (shortName) {
		case "carousel":
			return DigitalType.CAROUSEL;

		case "igtv_mobile":
			return DigitalType.IGTVMOBILE;

		case "igtv_produced":
			return DigitalType.IGTVPRODUCED;
			
		case "live":
			return DigitalType.LIVE;
			
		case "live_ob":
			return DigitalType.LIVEOB;

		case "live_studio":
			return DigitalType.LIVESTUDIO;
			
		case "static":
			return DigitalType.STATIC;
			
		case "story":
			return DigitalType.STORY;

		case "tweet":
			return DigitalType.TWEET;
			
		case "video_mobile":
			return DigitalType.VIDEOMOBILE;
			
		case "video_produced":
			return DigitalType.VIDEOPRODUCED;	
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
