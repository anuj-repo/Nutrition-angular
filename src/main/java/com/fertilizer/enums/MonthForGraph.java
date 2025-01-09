package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MonthForGraph {
	Jan("1"),Feb("2"),Mar("3"),Apr("4"),May("5"),Jun("6"),Jul("7"),Aug("8"),Sep("9"),Oct("10"),Nov("11"), Dec("12");
 
	private String name;

	private MonthForGraph(String name) {
		this.name = name;
	}
	@JsonValue
	public String getName() {
		return this.name;
	}
	
	public static MonthForGraph fromShortName(String shortName) {
		switch (shortName) {
		case "1":
			return MonthForGraph.Jan;
		case "01":
			return MonthForGraph.Jan;
		case "2":
			return MonthForGraph.Feb;
		case "02":
			return MonthForGraph.Feb;	
		case "3":
			return MonthForGraph.Mar;
		case "03":
			return MonthForGraph.Mar;	
		case "4":
			return MonthForGraph.Apr;
		case "04":
			return MonthForGraph.Apr;	
		case "5":
			return MonthForGraph.May;
		case "05":
			return MonthForGraph.May;	
		case "6":
			return MonthForGraph.Jun;
		case "06":
			return MonthForGraph.Jun;	
		case "7":
			return MonthForGraph.Jul;
		case "07":
			return MonthForGraph.Jul;	
		case "8":
			return MonthForGraph.Aug;
		case "08":
			return MonthForGraph.Aug;	
		case "9":
			return MonthForGraph.Sep;
		case "09":
			return MonthForGraph.Sep;
		case "10":
			return MonthForGraph.Oct;
			
		case "11":
			return MonthForGraph.Nov;
			
		case "12":
			return MonthForGraph.Dec;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
