package com.fertilizer.enums;

public enum CancellationStatus {
	
	
	REQUESTRAISEDBYAGENCY("request_raised_by_agency"), REQUESTREJECTEDBYADMIN("request_rejected_by_admin"), REQUESTACCEPTEDBYADMIN("request_accepted_by_admin");

	private String name;

	private CancellationStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public static boolean isValidAdProductClassification(String stateName) {
		for (CancellationStatus state : CancellationStatus.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return true;
			}
		}
		return false;
	}

	public static CancellationStatus fromShortName(String shortName) {

		switch (shortName) {
		case "request_raised_by_agency":
			return CancellationStatus.REQUESTRAISEDBYAGENCY;

		case "request_rejected_by_admin":
			return CancellationStatus.REQUESTREJECTEDBYADMIN;

		case "request_accepted_by_admin":
			return CancellationStatus.REQUESTACCEPTEDBYADMIN;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
