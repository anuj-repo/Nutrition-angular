package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EditActionRequestFor {
	INSERT("insert"),RO("ro"), PDC("pdc"),ADDITIONALSPECIFICATION("additionalSpecification");

		private final String name;

		private EditActionRequestFor(String name) {
			this.name = name;
		}

		@JsonValue
		public String getName() {
			return this.name;
		}

		@JsonCreator
		public static EditActionRequestFor editActionRequestFor(String name) {
			for (EditActionRequestFor state : EditActionRequestFor.values()) {
				if (state.getName().equalsIgnoreCase(name)) {
					return state;
				}
			}
			throw new IllegalArgumentException("Unknown Edit Action Request For name " + name);
		}
		
		public static EditActionRequestFor fromShortName(String shortName) {
			switch (shortName) {
			case "insert":
				return EditActionRequestFor.RO;
			case "ro":
				return EditActionRequestFor.RO;

			case "pdc":
				return EditActionRequestFor.PDC;
				
			case "additionalSpecification":
				return EditActionRequestFor.ADDITIONALSPECIFICATION;
				

			default:
				throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
			}
		}
	
}
