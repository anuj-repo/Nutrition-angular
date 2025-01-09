package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CouponType {
	GENERAL("general"), SEASONAL("seasonal"),FOR_USER("for_user");

	private final String name;

	private CouponType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	@JsonCreator
	public static CouponType forWholeDayName(String name) {
		for (CouponType state : CouponType.values()) {
			if (state.getName().equalsIgnoreCase(name)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Day name " + name);
	}
	
	public static CouponType fromShortName(String shortName) {
		switch (shortName) {
		case "general":
			return CouponType.GENERAL;

		case "seasonal":
			return CouponType.SEASONAL;
			
		case "for_user":
			return CouponType.FOR_USER;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
