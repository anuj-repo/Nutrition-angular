package com.fertilizer.enums;


public enum TemplateConstant {

	WELCOME_MAIL_TEMPLATE("/templates/welcome-user.html"),COMISSION_DISTRIBUTION("/templates/comission-distribution.html");
	
	private final String name;

	TemplateConstant(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
