package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EditStatus {

	NOTEDITED("0"),
	EDITREQUESTED("1"),
	EDITAPPROVED("2"),
	EDITREJECTED("3"),
	EDITREQUESTEDBYADMIN("4"),
	EDITCONFIRMATIONBYUSER("5");

	private final String name;

	EditStatus(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	public static EditStatus forStatusName(String stateName) {
		for (EditStatus state : EditStatus.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown Status name " + stateName);
	}

	public static EditStatus fromShortName(String shortName) {
		switch (shortName) {
		case "0":
			return EditStatus.NOTEDITED;

		case "1":
			return EditStatus.EDITREQUESTED;

		case "2":
			return EditStatus.EDITAPPROVED;

		case "3":
			return EditStatus.EDITREJECTED;
		case "4":
			return EditStatus.EDITREQUESTEDBYADMIN;

		case "5":
			return EditStatus.EDITCONFIRMATIONBYUSER;

			
			
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
