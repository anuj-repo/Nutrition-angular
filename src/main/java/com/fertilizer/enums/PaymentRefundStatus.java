package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

/**
 * @author imran
 *
 */
@Getter
public enum PaymentRefundStatus {
	
	//	DATABASE MAPPING
	//	0-> Not Applicable, 1->Initiated, 2-> Refuded

	NOTAPPLICABLE("0"), INITIATED("1"), REFUNDED("2"), REFUNDCALLFAILED("3"),ERRORINREFUND("4");

	private final String name;

	PaymentRefundStatus(String name) {
		this.name = name;
	}

	@JsonCreator
	public static PaymentRefundStatus forWholeDayName(String stateName) {
		for (PaymentRefundStatus state : PaymentRefundStatus.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown Payment Refund Status name " + stateName);
	}

	public static PaymentRefundStatus fromShortName(String shortName) {
		switch (shortName) {
		case "0":
			return PaymentRefundStatus.NOTAPPLICABLE;
		case "1":
			return PaymentRefundStatus.INITIATED;
		case "2":
			return PaymentRefundStatus.REFUNDED;
		case "3":
			return PaymentRefundStatus.REFUNDCALLFAILED;
		case "4":
			return PaymentRefundStatus.ERRORINREFUND;
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
