package com.fertilizer.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "states")
@DynamicUpdate
@Getter
@Setter
public class State extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5341295528107697835L;

	private Long countryId;

	private String stateName;

	private String stateCode;

}
