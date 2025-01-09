package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GenderEnum {

	MALE("Male"), FEMALE("Female");

	private final String name;

	GenderEnum(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	public static GenderEnum forStatusName(String stateName) {
		for (GenderEnum state : GenderEnum.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown FileUploadEnum name " + stateName);
	}

	public static GenderEnum fromShortName(String shortName) {
		switch (shortName) {
		case "Male":
			return GenderEnum.MALE;

		case "Female":
			return GenderEnum.FEMALE;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
