package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author IMRAN
 *
 */
public enum CustType {
	All("0"),
	AGENCY("1"),
	DIRECTUSER("2");
	
	private final String name;

	CustType(String name) {
		this.name = name;
	}
	
	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static CustType forWholeDayName(String stateName) {
		for (CustType state : CustType.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Day name " + stateName);
	}
	
	public static CustType fromShortName(String shortName) {
		switch (shortName) {
		case "0":
			return CustType.All;
		case "1":
			return CustType.AGENCY;
		case "2":
			return CustType.DIRECTUSER;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
