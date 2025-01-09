package com.fertilizer.enums;

public enum RadioNfctType {
	BRKEN("brk_en"),BRKIN("brk_in"),BRKOT("brk_ot"),LINKSCONTEST("links_contest"),LINKSOB("links_ob"),PROMOBRK("promo_brk"),
	RJVISIT("rj_visit"),RJMENTION("rj_mention"),SHOWSWEEP("show_sweep"),SHOWTAG("show_tag"),TIMECHECK("time_check"),
	TOPOFTHEHOUR("top_of_the_hour"),TRAFFICUPDATE("traffic_update");
	
	private String name;

	private RadioNfctType(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public static RadioNfctType fromShortName(String shortName) {
		switch(shortName) {
		case "brk_en":
			return RadioNfctType.BRKEN;

		case "brk_in":
			return RadioNfctType.BRKIN;
			
		case "brk_ot":
			return RadioNfctType.BRKOT;
			
		case "links (contest)":
			return RadioNfctType.LINKSCONTEST;	

		case "links_contest":
			return RadioNfctType.LINKSCONTEST;
			
		case "links (ob)":
			return RadioNfctType.LINKSOB;	
			
		case "links_ob":
			return RadioNfctType.LINKSOB;			

		case "promo_brk":
			return RadioNfctType.PROMOBRK;
			
		case "rj visit/studio shift (unit)":
			return RadioNfctType.RJVISIT;
			
		case "rj_visit":
			return RadioNfctType.RJVISIT;

		case "rj_mention":
			return RadioNfctType.RJMENTION;
			
		case "show_sweep":
			return RadioNfctType.SHOWSWEEP;
			
		case "show sweep":
			return RadioNfctType.SHOWSWEEP;
			
		case "show_tag":
			return RadioNfctType.SHOWTAG;
			
		case "time_check":
			return RadioNfctType.TIMECHECK;
			
		case "timecheck":
			return RadioNfctType.TIMECHECK;
			
		case "top_of_the_hour":
			return RadioNfctType.TOPOFTHEHOUR;
			
		case "top of the hour":
			return RadioNfctType.TOPOFTHEHOUR;
			
		case "traffic_update":
			return RadioNfctType.TRAFFICUPDATE;
			
		case "traffic update":
			return RadioNfctType.TRAFFICUPDATE;
			
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
		}
}
