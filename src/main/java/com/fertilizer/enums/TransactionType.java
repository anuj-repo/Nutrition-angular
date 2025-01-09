package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {

	CREDIT("cr"), DEBIT("dr");

	private final String name;

	TransactionType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	public static TransactionType forStatusName(String stateName) {
		for (TransactionType state : TransactionType.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown TransactionType name " + stateName);
	}

	public static TransactionType fromShortName(String shortName) {
		switch (shortName) {
		case "cr":
			return TransactionType.CREDIT;

		case "br":
			return TransactionType.DEBIT;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
