package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ApprovalStatus {
	NOTAPPROVED("0"), APPROVED("1"),REJECTED("2"),DEACTIVATED("3");

	private final String name;

	ApprovalStatus(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	public static ApprovalStatus forStatusName(String stateName) {
		for (ApprovalStatus state : ApprovalStatus.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown Admin Activated name " + stateName);
	}

	public static ApprovalStatus fromShortName(String shortName) {
		switch (shortName) {
		case "0":
			return ApprovalStatus.NOTAPPROVED;
		case "1":
			return ApprovalStatus.APPROVED;
		case "2":
			return ApprovalStatus.REJECTED;
		case "3":
			return ApprovalStatus.DEACTIVATED;

		default:
			throw new IllegalArgumentException("Status [" + shortName + "] not supported.");
		}
	}
}
