package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

/**
 * @author kabir
 *
 */
@Getter
public enum PaymentStatus {

	PENDING("0"), COMPLETED("1"), FAILED("2");

	private final String name;

	PaymentStatus(String name) {
		this.name = name;
	}

	@JsonCreator
	public static PaymentStatus forWholeDayName(String stateName) {
		for (PaymentStatus state : PaymentStatus.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown PaymentStatus name " + stateName);
	}

	public static PaymentStatus fromShortName(String shortName) {
		switch (shortName) {
		case "0":
			return PaymentStatus.PENDING;
		case "1":
			return PaymentStatus.COMPLETED;
		case "2":
			return PaymentStatus.FAILED;
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
