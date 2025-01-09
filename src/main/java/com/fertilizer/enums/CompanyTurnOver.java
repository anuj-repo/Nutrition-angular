package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CompanyTurnOver {
	UNDER1("upto1cr"),
	BETWEEN1TO50("1crto50cr"),
	BETWEEN50TO200("50crto200cr"),
	BETWEEN200TO500("200crto500cr"),
	ABOVE500("above500cr");
	
	private final String name;
	
	CompanyTurnOver(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
	
	@JsonCreator
	public static CompanyTurnOver forWholeDayName(String stateName) {
		for (CompanyTurnOver state : CompanyTurnOver.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Day name " + stateName);
	}
	
	public static CompanyTurnOver fromShortName(String shortName) {
		switch (shortName) {
		case "upto1cr":
			return CompanyTurnOver.UNDER1;
		case "1crto50cr":
			return CompanyTurnOver.BETWEEN1TO50;
		case "50crto200cr":
			return CompanyTurnOver.BETWEEN50TO200;
		case "200crto500cr":
			return CompanyTurnOver.BETWEEN200TO500;
		case "above500cr":
			return CompanyTurnOver.ABOVE500;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
	
	public String convertToHubspotValue() {
		switch (this.name) {
		case "upto1cr":
			return "Upto  INR 1 cr.";
		case "1crto50cr":
			return "INR 1 cr. to INR 50 cr.";
		case "50crto200cr":
			return "INR 50 cr. to  INR 200 cr.";
		case "200crto500cr":
			return "INR 200 cr. to INR 500 cr.";
		case "above500cr":
			return "Above INR 500 cr.";

		default:
			throw new IllegalArgumentException("ShortName [" + this.name + "] not supported.");
		}
	}
}
