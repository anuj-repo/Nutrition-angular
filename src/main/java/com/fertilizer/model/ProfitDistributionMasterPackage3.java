package com.fertilizer.model;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profit_distribution_master_package3")
@DynamicUpdate
@Getter
@Setter
public class ProfitDistributionMasterPackage3 {

	private static final long serialVersionUID = -2499830656527689214L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String category;
	private String categoryId;
	private String categoryUserEmail;
	private Double percentage;
	private String description;
}
