package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Dhiraj
 *
 */
public enum Source {
	SSO_AD("sso-ad"),
	SSO_CLS("sso-cls"),
	AD("ad"),
	CLS("cls");
	
	private final String name;

	Source(String name) {
		this.name = name;
	}
	
	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static Source forWholeDayName(String stateName) {
		for (Source state : Source.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Day name " + stateName);
	}
	
	public static Source fromShortName(String shortName) {
		switch (shortName) {
		case "sso-ad":
			return Source.SSO_AD;
		case "sso-cls":
			return Source.SSO_CLS;
		case "ad":
			return Source.AD;
		case "cls":
			return Source.CLS;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
	
}
