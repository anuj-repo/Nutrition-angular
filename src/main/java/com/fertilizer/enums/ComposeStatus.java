package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

/**
 * @author imran
 *
 */
@Getter
public enum ComposeStatus {
	
	//	DATABASE MAPPING
	//	0->Pending from user, 1-> Composed by User, 2-> Composed By Admin, 3-> Approved by Admin, 4-> Rejected By Admin, 5-> Cancelled

	PENDINGFROMUSER("0"), COMPOSEBYUSER("1"), COMPOSEBYADMIN("2"), APPROVEDBYADMIN("3"), REJECTEDBYADMIN("4"), CANCELLATIONRAISEDBYAGENCY("5"),CANCELLATIONREJECTEDBYADMIN("6"),CANCELLATIONAPPROVEDBYADMIN("7");

	private final String name;

	ComposeStatus(String name) {
		this.name = name;
	}

	@JsonCreator
	public static ComposeStatus forWholeDayName(String stateName) {
		for (ComposeStatus state : ComposeStatus.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown Compose Status name " + stateName);
	}

	public static ComposeStatus fromShortName(String shortName) {
		switch (shortName) {
		case "0":
			return ComposeStatus.PENDINGFROMUSER;
		case "1":
			return ComposeStatus.COMPOSEBYUSER;
		case "2":
			return ComposeStatus.COMPOSEBYADMIN;
		case "3":
			return ComposeStatus.APPROVEDBYADMIN;
		case "4":
			return ComposeStatus.REJECTEDBYADMIN;
		case "5":
			return ComposeStatus.CANCELLATIONRAISEDBYAGENCY;
		case "6":
			return ComposeStatus.CANCELLATIONREJECTEDBYADMIN;
		case "7":
			return ComposeStatus.CANCELLATIONAPPROVEDBYADMIN;
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
