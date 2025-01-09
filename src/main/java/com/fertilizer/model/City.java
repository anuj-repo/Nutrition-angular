package com.fertilizer.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cities")
@DynamicUpdate
@Getter
@Setter
public class City extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long countryId;

	private Long stateId;

	@NotBlank(message = "{validation.cityName.NotBlank}")
	private String cityName;

	private String cityType;

	private String cityCode;
	
}
