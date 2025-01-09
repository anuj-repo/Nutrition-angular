package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CustomerPortalRequestBy {

	ADWORKS("adworks"), CUSTOMERPORTAL("customer-portal");

	// REJECTCLIENT("reject-client"), DEACTIVATECLIENT("deactivate-client")
	private final String name;

	private CustomerPortalRequestBy(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	public static CustomerPortalRequestBy fromShortName(String shortName) {
		switch (shortName) {
		case "adworks":
			return CustomerPortalRequestBy.ADWORKS;

		case "customer-portal":
			return CustomerPortalRequestBy.CUSTOMERPORTAL;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
