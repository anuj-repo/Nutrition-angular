package com.fertilizer.enums;

public enum RadioProductType {
	 FCT("fct"),NFCT("nfct"),DIGITAL("digital");

	private String name;

	private RadioProductType(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public static RadioProductType fromShortName(String shortName) {
		switch (shortName) {
		case "fct":
			return RadioProductType.FCT;

		case "nfct":
			return RadioProductType.NFCT;
			
		case "non - fct":
			return RadioProductType.NFCT;
			
		case "digital":
			return RadioProductType.DIGITAL;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
