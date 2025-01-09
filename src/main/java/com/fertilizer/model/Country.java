package com.fertilizer.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "countries")
@DynamicUpdate
@Getter
@Setter
public class Country extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2499830656527689214L;

	private String countryName;

	private String countryCodeShort;

	private String countryCodeLong;

	private Long countryNumericCode;

	private String countryStdCode;

	private String region;

	private String subRegion;

}
