package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OtpFor {
	PHONE("phone"), EMAIL("email");

	private final String name;

	private OtpFor(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	@JsonCreator
	public static OtpFor forWholeDayName(String name) {
		for (OtpFor state : OtpFor.values()) {
			if (state.getName().equalsIgnoreCase(name)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Day name " + name);
	}
	
	public static OtpFor fromShortName(String shortName) {
		switch (shortName) {
		case "phone":
			return OtpFor.PHONE;

		case "email":
			return OtpFor.EMAIL;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
