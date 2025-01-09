package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

/**
 * @author kabir
 *
 */
@Getter
public enum PaymentMode {

	CASH("cash"), CHEQUE("cheque"), ONLINE("online");

	private final String name;

	PaymentMode(String name) {
		this.name = name;
	}

	@JsonCreator
	public static PaymentMode forWholeDayName(String stateName) {
		for (PaymentMode state : PaymentMode.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown PaymentMode name " + stateName);
	}

	public static PaymentMode fromShortName(String shortName) {
		switch (shortName) {
		case "cash":
			return PaymentMode.CASH;
		case "cheque":
			return PaymentMode.CHEQUE;
		case "online":
			return PaymentMode.ONLINE;
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
