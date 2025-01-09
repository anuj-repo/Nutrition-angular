package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DigitalChannelName {
	FACEBOOK("facebook"), INSTAGRAM("instagram"), TWITTER("twitter"), YOUTUBE("youtube");

	private final String name;

	private DigitalChannelName(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	@JsonCreator
	public static DigitalChannelName forWholeDayName(String name) {
		for (DigitalChannelName state : DigitalChannelName.values()) {
			if (state.getName().equalsIgnoreCase(name)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Digital Channel Name " + name);
	}
	
	public static DigitalChannelName fromShortName(String shortName) {
		switch (shortName) {
		case "facebook":
			return DigitalChannelName.FACEBOOK;

		case "instagram":
			return DigitalChannelName.INSTAGRAM;
			
		case "twitter":
			return DigitalChannelName.TWITTER;

		case "youtube":
			return DigitalChannelName.YOUTUBE;
			
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
