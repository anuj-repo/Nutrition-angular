package com.fertilizer.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Dhiraj
 *
 */
@Entity
@Table(name="user_activity_types")
@Getter
@Setter
public class UserActivityTypes extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8772402892293758411L;
	
	private String activityName;
	
	private String activityIdentifier;
	
	private String activityTable;

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityIdentifier() {
		return activityIdentifier;
	}

	public void setActivityIdentifier(String activityIdentifier) {
		this.activityIdentifier = activityIdentifier;
	}

	public String getActivityTable() {
		return activityTable;
	}

	public void setActivityTable(String activityTable) {
		this.activityTable = activityTable;
	}
}
