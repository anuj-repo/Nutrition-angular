package com.fertilizer.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fertilizer.enums.QueryStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_queries")
@DynamicUpdate
@Getter
@Setter
public class Query extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3507440003781902129L;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "user_id")
	private User user;

	private String name;

	private String emailAddress;

	private String phone;

	private String description;
	
	private Long hubspotId;
	
	private String hubspotResponse;

	private QueryStatus queryStatus = QueryStatus.PENDING;
	

}
