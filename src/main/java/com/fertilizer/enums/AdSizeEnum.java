package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AdSizeEnum {
		SQCM("sqcm"),S("s");

		private String name;

		private AdSizeEnum(String name) {
			this.name = name;
		}
		@JsonValue
		public String getName() {
			return this.name;
		}
		
		public static AdSizeEnum fromShortName(String shortName) {
			switch (shortName) {
			case "sqcm":
				return AdSizeEnum.SQCM;

			case "s":
				return AdSizeEnum.S;

			default:
				throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
			}
		}
}
