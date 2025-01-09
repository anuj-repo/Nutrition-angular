package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PurchaseFor {
	
	SELF("self"), CLIENT("client");
	private final String name;

	
	private PurchaseFor(String name) {
		this.name = name;
	}
	
	@JsonValue
	public String getName() {
		return this.name;
	}

	
	public static PurchaseFor fromShortName(String shortName) {
		switch (shortName) {
		case "self":
			return PurchaseFor.SELF;

		case "client":
			return PurchaseFor.CLIENT;

	
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
