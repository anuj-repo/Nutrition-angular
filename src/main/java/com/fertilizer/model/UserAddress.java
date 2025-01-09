package com.fertilizer.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fertilizer.enums.AddressType;
import com.fertilizer.enums.BooleanEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_address")
@DynamicUpdate
@Getter
@Setter
public class UserAddress extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private String address;

	private String state;
	
	private Long stateId;
	
	private Long countryId;

	private String country;

	private String pincode;

	private String city;

	private Long cityId;
	
	private BooleanEnum  isDefault ;
	
	private AddressType addressType;
	
	private BooleanEnum isOtherCity;

}
