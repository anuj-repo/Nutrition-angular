package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Dhiraj
 *
 */
public enum BooleanEnum {
	NO("0"), YES("1");

	private final String name;

	BooleanEnum(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static BooleanEnum forWholeDayName(String stateName) {
		for (BooleanEnum state : BooleanEnum.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown boolean value " + stateName);
	}
	
	public static BooleanEnum fromShortName(String shortName) {
		switch (shortName) {
		case "0":
			return BooleanEnum.NO;

		case "1":
			return BooleanEnum.YES;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
	
	public String convertToHubspotValue() {
		switch (this.name) {
		case "0":
			return "no";

		case "1":
			return "yes";

		default:
			throw new IllegalArgumentException("ShortName [" + this.name + "] not supported.");
		}
	}
}
