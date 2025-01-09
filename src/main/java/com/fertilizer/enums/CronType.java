package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Prashant
 *
 */
public enum CronType {
	DELETEREQUESTRESPONSELOGS("deleteRequestResponseLogs", 3L, "Runs Manully or by Cron Daily 9:30 PM");
	

	private final String name;
	private final String remarks;
	private final Long id;

	CronType(String name, Long id, String remarks) {
		this.name = name;
		this.remarks = remarks;
		this.id = id;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	@JsonValue
	public String getRemarks() {
		return this.remarks;
	}

	@JsonValue
	public Long getId() {
		return this.id;
	}

}
