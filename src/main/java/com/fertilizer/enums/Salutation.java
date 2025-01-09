package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Salutation {
	
	MR("Mr."),
	MRS("Mrs."),
	MISS("Miss."),
	MS("Ms.");
	
	private final String name;

	Salutation(String name) {
		this.name = name;
	}
	
	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static Salutation forWholeDayName(String stateName) {
		for (Salutation state : Salutation.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Day name " + stateName);
	}
	
	public static Salutation fromShortName(String shortName) {
		switch (shortName) {
		case "Mr.":
			return Salutation.MR;
		case "Mrs.":
			return Salutation.MRS;
		case "Miss.":
			return Salutation.MISS;
		case "Ms.":
			return Salutation.MS;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
