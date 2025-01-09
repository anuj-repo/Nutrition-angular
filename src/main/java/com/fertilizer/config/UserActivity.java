package com.fertilizer.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Dhiraj
 *
 */
@Getter
@Setter
public class UserActivity {

	private Long id;

	@JsonProperty(value = "activity_name")
	private String activityName;

	@JsonProperty(value = "activity_identifier")
	private String activityIdentifier;

	@JsonProperty(value = "activity_table")
	private String activityTable;
}