package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BooleanYNEnum {
	N("0"), Y("1");
	
	private final String name;
	
	BooleanYNEnum(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}
	
	public static BooleanYNEnum forStatusName(String stateName) {
		for (BooleanYNEnum state : BooleanYNEnum.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown Boolean " + stateName);
	}
	
	public static BooleanYNEnum fromShortName(String shortName) {
		switch (shortName) {
		case "0":
			return BooleanYNEnum.N;

		case "1":
			return BooleanYNEnum.Y;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
