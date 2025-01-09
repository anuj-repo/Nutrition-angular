package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DiscountType {
	FIXED("fixed"), PERCENT("percent");

	private final String name;

	private DiscountType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	@JsonCreator
	public static DiscountType forWholeDayName(String name) {
		for (DiscountType state : DiscountType.values()) {
			if (state.getName().equalsIgnoreCase(name)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Day name " + name);
	}
	
	public static DiscountType fromShortName(String shortName) {
		switch (shortName) {
		case "fixed":
			return DiscountType.FIXED;

		case "percent":
			return DiscountType.PERCENT;
			

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
