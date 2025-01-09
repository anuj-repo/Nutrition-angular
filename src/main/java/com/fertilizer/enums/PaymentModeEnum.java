package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

/**
 * @author imran
 *
 */
@Getter
public enum PaymentModeEnum {
	
	//	DATABASE MAPPING
	//	online-> ONLINE, credit->CREDIT

	ONLINE("online"), CREDIT("credit"), PDC("pdc");

	private final String name;

	PaymentModeEnum(String name) {
		this.name = name;
	}

	@JsonCreator
	public static PaymentModeEnum forWholeDayName(String stateName) {
		for (PaymentModeEnum state : PaymentModeEnum.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown payment Mode Enum name " + stateName);
	}

	public static PaymentModeEnum fromShortName(String shortName) {
		switch (shortName) {
		case "online":
			return PaymentModeEnum.ONLINE;
		case "credit":
			return PaymentModeEnum.CREDIT;
		case "pdc":
			return PaymentModeEnum.PDC;
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
