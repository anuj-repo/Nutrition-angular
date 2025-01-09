package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NumberOfUse {
	SINGLE("single"), MULTIPLE("multiple");
	
	private String name;

	private NumberOfUse(String name) {
		this.name = name;
	}
	@JsonValue
	public String getName() {
		return this.name;
	}
	
	public static NumberOfUse fromShortName(String shortName) {
		switch (shortName) {
		case "single":
			return NumberOfUse.SINGLE;

		case "multiple":
			return NumberOfUse.MULTIPLE;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
