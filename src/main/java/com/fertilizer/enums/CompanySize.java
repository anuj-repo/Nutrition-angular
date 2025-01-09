package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Dhiraj
 *
 */
public enum CompanySize {
	UNDER10("under10"),
	BETWEEN10TO100("10to100"),
	BETWEEN100TO500("100to500."),
	BETWEEN500TO1000("500to1000."),
	ABOVE1000("above1000");
	
	
	private final String name;

	CompanySize(String name) {
		this.name = name;
	}
	
	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static CompanySize forWholeDayName(String stateName) {
		for (CompanySize state : CompanySize.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Day name " + stateName);
	}
	
	public static CompanySize fromShortName(String shortName) {
		switch (shortName) {
		case "under10":
			return CompanySize.UNDER10;
		case "10to100":
			return CompanySize.BETWEEN10TO100;
		case "100to500":
			return CompanySize.BETWEEN100TO500;
		case "500to1000":
			return CompanySize.BETWEEN500TO1000;
		case "above1000":
			return CompanySize.ABOVE1000;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
