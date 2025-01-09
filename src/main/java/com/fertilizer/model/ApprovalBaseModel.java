package com.fertilizer.model;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Dhiraj
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class ApprovalBaseModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4214079793054993569L;

	protected Long approvedBy;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date approvedAt;

	public Long getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(Date approvedAt) {
		this.approvedAt = approvedAt;
	}
}