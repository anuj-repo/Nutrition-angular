package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CustomerPortalRequestFor {

	USERDETAIL("user-detail"), LOGIN("login");

	private final String name;

	private CustomerPortalRequestFor(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	public static CustomerPortalRequestFor fromShortName(String shortName) {
		switch (shortName) {
		case "user-detail":
			return CustomerPortalRequestFor.USERDETAIL;

		case "login":
			return CustomerPortalRequestFor.LOGIN;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
