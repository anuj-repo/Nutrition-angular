package com.fertilizer.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicUpdate;

import com.fertilizer.enums.BooleanEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@DynamicUpdate
@Getter
@Setter
public class Permission extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String label;

	private String uiLinks;

	private String guardName;
}
