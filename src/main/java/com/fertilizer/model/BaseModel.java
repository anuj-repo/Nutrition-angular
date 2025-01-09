package com.fertilizer.model;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fertilizer.enums.Status;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {}, allowGetters = true)
@Getter
@Setter
public abstract class BaseModel extends ParentBaseModel {
	private static final long serialVersionUID = 4629436281788181234L;

	// @Enumerated(EnumType.STRING)
	protected Status status = Status.ACTIVE;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
